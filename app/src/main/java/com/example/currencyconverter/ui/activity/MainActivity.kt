package com.example.currencyconverter.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyconverter.R
import com.example.currencyconverter.data.model.Currency
import com.example.currencyconverter.data.model.Rates
import com.example.currencyconverter.delegate.CurrencyDelegate
import com.example.currencyconverter.util.viewModelProvider
import com.example.currencyconverter.viewmodel.MainViewModelImpl
import com.revolut.recyclerkit.delegates.DelegatesManager
import com.revolut.recyclerkit.delegates.ListItem
import com.revolut.rxdiffadapter.RxDiffAdapter
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: MainViewModelImpl
    private val disposable = CompositeDisposable()
    private var baseCurrency = "EUR"
    private val mAdapter by lazy {
        RxDiffAdapter(
            DelegatesManager().addDelegate(
                CurrencyDelegate(
                    { model -> onClick(model)},
                    { value -> }
                )
            ),
        true
        )
    }

    companion object {
        fun intent(context: Context) : Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            return intent
        }
    }

    private fun onClick(model: CurrencyDelegate.Model) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = viewModelProvider(viewModelFactory)

        activity_main_recycler_view.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }

        disposable.add(viewModel.getCurrencyRatesObservable().subscribe { data ->
            if (data.error == null) {
                data.content?.let { currency ->
                    val items = createItems(currency)
                    mAdapter.setItems(items)
                }
            }
        })

    }

    override fun onResume() {
        super.onResume()
        viewModel.requestCurrencyRates(baseCurrency, false)
    }

    private fun createItems(currency: Currency): MutableList<ListItem> {
        val items = mutableListOf<ListItem>(
            CurrencyDelegate.Model(
                "0",
                currency.baseCurrency,
                1.0
            )
        )
        items.addAll(Rates::class.java.declaredFields
            .filter { field ->
                field.isAccessible = true
                field.get(currency.rates) != null
            }
            .mapIndexed { index, field ->
                field.isAccessible = true
                //id is index + 1 because first item will always display base currency
                CurrencyDelegate.Model(
                    (index + 1).toString(),
                    field.name,
                    field.get(currency.rates) as Double
                )
            }
        )
        return items
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!disposable.isDisposed) disposable.dispose()
    }
}