package com.example.currencyconverter.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*
import com.example.currencyconverter.R
import com.example.currencyconverter.data.model.Currency
import com.example.currencyconverter.data.model.Rates
import com.example.currencyconverter.delegate.CurrencyDelegate
import com.example.currencyconverter.util.viewModelProvider
import com.example.currencyconverter.util.waitForLayout
import com.example.currencyconverter.viewmodel.MainViewModelImpl
import com.revolut.recyclerkit.delegates.DelegatesManager
import com.revolut.rxdiffadapter.RxDiffAdapter
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: MainViewModelImpl
    private val disposable = CompositeDisposable()
    private var baseCurrency = "EUR"
    private var multiplier: Float = 1f
    private val mAdapter by lazy {
        RxDiffAdapter(
            DelegatesManager().addDelegate(
                CurrencyDelegate(
                    { model, position -> onClick(model, position)},
                    { value -> onValueChanged(value) }
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

    private fun onClick(model: CurrencyDelegate.Model, position: Int) {
        baseCurrency = model.currencyCode
        multiplier = model.currencyValue
        viewModel.setBaseCurrency(baseCurrency)
        viewModel.requestCurrencyRatesImmediately(baseCurrency, true)
        animationsSupported(true)
        moveItems(position, 0)
        mAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount)
                animationsSupported(false)
            }
        })
    }

    private fun onValueChanged(value: String) {
        activity_main_recycler_view.waitForLayout {
            val numericVal = try {
                value.toFloat()
            } catch (e: Exception) {
                0f
            }
            mAdapter.items.subList(1, mAdapter.itemCount).forEachIndexed { index, listItem ->
                val listModel = listItem as CurrencyDelegate.Model
                listModel.currencyValue = if (multiplier != 0f) {
                    numericVal * (listModel.currencyValue / multiplier)
                } else {
                    0f
                }
                mAdapter.updateItem(index + 1, listModel)
            }
            multiplier = numericVal
        }
    }

    private fun moveItems(fromPos: Int, toPos: Int) {
        val fromPosItem = mAdapter.getItem(fromPos)
        val newItems = mAdapter.items.toMutableList()
        newItems.removeAt(fromPos)
        newItems.add(toPos, fromPosItem)
        mAdapter.setItems(newItems)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = viewModelProvider(viewModelFactory)

        setUpRecyclerView()

        disposable.add(viewModel.getCurrencyRatesObservable().subscribe { data ->
            if (data.error == null) {
                data.content?.let { currency ->
                    if (currency.baseCurrency == baseCurrency) {
                        if (mAdapter.itemCount == 0) {
                            mAdapter.setItems(createItems(currency))
                        } else {
                            updateCurrencyValues(currency)
                        }
                    }
                }
            }
        })

    }

    private fun updateCurrencyValues(currency: Currency) {
        mAdapter.items.forEachIndexed { index, listItem ->
            val listModel = listItem as CurrencyDelegate.Model
            val field = Rates::class.java.getDeclaredField(listModel.currencyCode.toUpperCase(
                Locale.getDefault())
            )
            field.isAccessible = true
            field.get(currency.rates)?.let { currencyValue ->
                listModel.currencyValue = (currencyValue as Float) * multiplier
                mAdapter.updateItem(index, listModel)
            }
        }
    }

    private fun setUpRecyclerView() {
        activity_main_recycler_view.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
            animationsSupported(false)
            setHasFixedSize(true)
        }
    }

    private fun animationsSupported(supported: Boolean) {
        (activity_main_recycler_view.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = supported
    }

    override fun onPause() {
        viewModel.cancelUpdateRequests()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        viewModel.startUpdateRequests()
    }

    private fun createItems(currency: Currency): MutableList<CurrencyDelegate.Model> {
        val items = mutableListOf(
            CurrencyDelegate.Model(
                "0",
                currency.baseCurrency,
                multiplier
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
                val currencyVal = field.get(currency.rates) as Float
                CurrencyDelegate.Model(
                    (index + 1).toString(),
                    field.name,
                    currencyVal * multiplier
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