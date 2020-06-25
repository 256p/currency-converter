package com.example.currencyconverter.delegate

import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.example.currencyconverter.R
import com.example.currencyconverter.util.inflate
import com.revolut.recyclerkit.delegates.BaseRecyclerViewDelegate
import com.revolut.recyclerkit.delegates.BaseRecyclerViewHolder
import com.revolut.recyclerkit.delegates.ListItem
import kotlinx.android.synthetic.main.currency_delegate.view.*
import java.util.*

class CurrencyDelegate(
    private val onClickListener: (Model) -> Unit,
    private val onValueChangedListener: (String) -> Unit
) : BaseRecyclerViewDelegate<CurrencyDelegate.Model, CurrencyDelegate.ViewHolder>(
        R.layout.currency_delegate,
        {_, data -> data is Model}
    ) {

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder =
        ViewHolder(parent.inflate(R.layout.currency_delegate, false))

    override fun onBindViewHolder(holder: ViewHolder, data: Model, pos: Int, payloads: List<Any>?) {
        super.onBindViewHolder(holder, data, pos, payloads)
        holder.takeIf { payloads.isNullOrEmpty() }?.applyData(data)
    }

    private fun ViewHolder.applyData(data: Model) {
        itemView.setOnClickListener { onClickListener(data) }
        image.loadImage(getCountryFlagByCode(data.currencyCode))
        currencyCode.text = data.currencyCode
        currencyName.text = getCurrencyNameByCode(data.currencyCode)
        currencyValue.setText(data.currencyValue.toString())
    }

    private fun ImageView.loadImage(@DrawableRes imageRes: Int) =
        Glide.with(this)
            .load(imageRes)
            .circleCrop()
            .into(this)

    //currency code according to ISO 4217
    private fun getCountryFlagByCode(currencyCode: String): Int =
        when (currencyCode.toLowerCase(Locale.getDefault())) {
            "nzd" -> R.drawable.nz
            "aud" -> R.drawable.au
            "eur" -> R.drawable.eu
            "gbp" -> R.drawable.gb
            "usd" -> R.drawable.us
            "hkd" -> R.drawable.hk
            "cad" -> R.drawable.ca
            "jpy" -> R.drawable.jp
            "afn" -> R.drawable.af
            "all" -> R.drawable.al
            "dzd" -> R.drawable.dz
            "xcd" -> R.drawable.ag
            "ars" -> R.drawable.ar
            "amd" -> R.drawable.am
            "ang" -> R.drawable.an
            "azn" -> R.drawable.az
            "bsd" -> R.drawable.bs
            "bhd" -> R.drawable.bh
            "bdt" -> R.drawable.bd
            "bbd" -> R.drawable.bb
            "byr" -> R.drawable.by
            "bzd" -> R.drawable.bz
            "xof" -> R.drawable.sn
            "bmd" -> R.drawable.bm
            "inr" -> R.drawable.`in`
            "bob" -> R.drawable.bo
            "bwp" -> R.drawable.bw
            "nok" -> R.drawable.no
            "brl" -> R.drawable.br
            "bnd" -> R.drawable.bn
            "bgn" -> R.drawable.bg
            "bif" -> R.drawable.bi
            "khr" -> R.drawable.kh
            "xaf" -> R.drawable.cm
            "cve" -> R.drawable.cv
            "kyd" -> R.drawable.ky
            "clp" -> R.drawable.cl
            "cny" -> R.drawable.cn
            "cop" -> R.drawable.co
            "kmf" -> R.drawable.km
            "cdf" -> R.drawable.cd
            "crc" -> R.drawable.cr
            "hrk" -> R.drawable.hr
            "cup" -> R.drawable.cu
            "cyp" -> R.drawable.cy
            "czk" -> R.drawable.cz
            "dkk" -> R.drawable.dk
            "djf" -> R.drawable.dj
            "dop" -> R.drawable._do
            "idr" -> R.drawable.id
            "ecs" -> R.drawable.ec
            "egp" -> R.drawable.eg
            "svc" -> R.drawable.sv
            "etb" -> R.drawable.et
            "eek" -> R.drawable.ee
            "fkp" -> R.drawable.fk
            "fjd" -> R.drawable.fj
            "xpf" -> R.drawable.pf
            "gmd" -> R.drawable.gm
            "gel" -> R.drawable.ge
            "gip" -> R.drawable.gi
            "gtq" -> R.drawable.gt
            "gnf" -> R.drawable.gn
            "gyd" -> R.drawable.gy
            "htg" -> R.drawable.ht
            "hnl" -> R.drawable.hn
            "huf" -> R.drawable.hu
            "isk" -> R.drawable.`is`
            "irr" -> R.drawable.ir
            "iqd" -> R.drawable.iq
            "ils" -> R.drawable.il
            "jmd" -> R.drawable.jm
            "jod" -> R.drawable.jo
            "kzt" -> R.drawable.kz
            "kes" -> R.drawable.ke
            "kpw" -> R.drawable.kp
            "krw" -> R.drawable.kr
            "kwd" -> R.drawable.kw
            "kgs" -> R.drawable.kg
            "lvl" -> R.drawable.lv
            "lbp" -> R.drawable.lb
            "lsl" -> R.drawable.ls
            "lrd" -> R.drawable.lr
            "lyd" -> R.drawable.ly
            "chf" -> R.drawable.ch
            "ltl" -> R.drawable.lt
            "mop" -> R.drawable.mo
            "mkd" -> R.drawable.mk
            "mga" -> R.drawable.mg
            "mwk" -> R.drawable.mw
            "myr" -> R.drawable.my
            "mvr" -> R.drawable.mv
            "mtl" -> R.drawable.mt
            "mro" -> R.drawable.mr
            "mur" -> R.drawable.mu
            "mxn" -> R.drawable.mx
            "mdl" -> R.drawable.md
            "mnt" -> R.drawable.mn
            "mad" -> R.drawable.ma
            "mzn" -> R.drawable.mz
            "mmk" -> R.drawable.mm
            "nad" -> R.drawable.na
            "npr" -> R.drawable.np
            "nio" -> R.drawable.ni
            "ngn" -> R.drawable.ng
            "omr" -> R.drawable.om
            "pkr" -> R.drawable.pk
            "pab" -> R.drawable.pa
            "pgk" -> R.drawable.pg
            "pyg" -> R.drawable.py
            "pen" -> R.drawable.pe
            "php" -> R.drawable.ph
            "pln" -> R.drawable.pl
            "qar" -> R.drawable.qa
            "ron" -> R.drawable.ro
            "rub" -> R.drawable.ru
            "rwf" -> R.drawable.rw
            "std" -> R.drawable.st
            "sar" -> R.drawable.sa
            "scr" -> R.drawable.sc
            "sll" -> R.drawable.sl
            "sgd" -> R.drawable.sg
            "skk" -> R.drawable.sk
            "sbd" -> R.drawable.sb
            "sos" -> R.drawable.so
            "zar" -> R.drawable.za
            "lkr" -> R.drawable.lk
            "sdg" -> R.drawable.sd
            "srd" -> R.drawable.sr
            "szl" -> R.drawable.sz
            "sek" -> R.drawable.se
            "syp" -> R.drawable.sy
            "twd" -> R.drawable.tw
            "tjs" -> R.drawable.tj
            "tzs" -> R.drawable.tz
            "thb" -> R.drawable.th
            "top" -> R.drawable.to
            "ttd" -> R.drawable.tt
            "tnd" -> R.drawable.tn
            "try" -> R.drawable.tr
            "tmt" -> R.drawable.tm
            "ugx" -> R.drawable.ug
            "uah" -> R.drawable.ua
            "aed" -> R.drawable.ae
            "uyu" -> R.drawable.uy
            "uzs" -> R.drawable.uz
            "vuv" -> R.drawable.vu
            "vef" -> R.drawable.ve
            "vnd" -> R.drawable.vn
            "yer" -> R.drawable.ye
            "zmk" -> R.drawable.zm
            "zwd" -> R.drawable.zw
            "aoa" -> R.drawable.ao
            "aqd" -> R.drawable.aq
            "bam" -> R.drawable.ba
            "ghs" -> R.drawable.gh
            "ggp" -> R.drawable.gg
            "lak" -> R.drawable.la
            "rsd" -> R.drawable.rs
            else -> -1
        }

    private fun getCurrencyNameByCode(currencyCode: String): String =
        when (currencyCode.toLowerCase(Locale.getDefault())) {
            "nzd" -> "New Zealand Dollar"
            "aud" -> "Australian Dollar"
            "eur" -> "Euro"
            "gbp" -> "Sterling"
            "usd" -> "US Dollar"
            "hkd" -> "Hong Kong Dollar"
            "cad" -> "Canadian Dollar"
            "jpy" -> "Japanese Yen"
            "afn" -> "Afghani"
            "all" -> "Lek"
            "dzd" -> "Algerian Dinar"
            "xcd" -> "East Caribbean Dollar"
            "ars" -> "Peso"
            "amd" -> "Dram"
            "ang" -> "Netherlands Antilles Guilder"
            "azn" -> "Manat"
            "bsd" -> "Bahamian Dollar"
            "bhd" -> "Bahraini Dinar"
            "bdt" -> "Taka"
            "bbd" -> "Barbadian Dollar"
            "byr" -> "Belarus Ruble"
            "bzd" -> "Belizean Dollar"
            "xof" -> "CFA Franc BCEAO"
            "bmd" -> "Bermudian Dollar"
            "inr" -> "Indian Rupee"
            "bob" -> "Boliviano"
            "bwp" -> "Pula"
            "nok" -> "Norwegian Krone"
            "brl" -> "Brazil"
            "bnd" -> "Bruneian Dollar"
            "bgn" -> "Lev"
            "bif" -> "Burundi Franc"
            "khr" -> "Riel"
            "xaf" -> "CFA Franc BEAC"
            "cve" -> "Escudo"
            "kyd" -> "Caymanian Dollar"
            "clp" -> "Chilean Peso"
            "cny" -> "Yuan Renminbi"
            "cop" -> "Peso"
            "kmf" -> "Comoran Franc"
            "cdf" -> "Congolese Frank"
            "crc" -> "Costa Rican Colon"
            "hrk" -> "Croatian Dinar"
            "cup" -> "Cuban Peso"
            "cyp" -> "Cypriot Pound"
            "czk" -> "Koruna"
            "dkk" -> "Danish Krone"
            "djf" -> "Djiboutian Franc"
            "dop" -> "Dominican Peso"
            "idr" -> "Indonesian Rupiah"
            "ecs" -> "Sucre"
            "egp" -> "Egyptian Pound"
            "svc" -> "Salvadoran Colon"
            "etb" -> "Ethiopian Birr"
            "eek" -> "Estonian Kroon"
            "fkp" -> "Falkland Pound"
            "fjd" -> "Fijian Dollar"
            "xpf" -> "CFP Franc"
            "gmd" -> "Dalasi"
            "gel" -> "Lari"
            "gip" -> "Gibraltar Pound"
            "gtq" -> "Quetzal"
            "gnf" -> "Guinean Franc"
            "gyd" -> "Guyanaese Dollar"
            "htg" -> "Gourde"
            "hnl" -> "Lempira"
            "huf" -> "Forint"
            "isk" -> "Icelandic Krona"
            "irr" -> "Iranian Rial"
            "iqd" -> "Iraqi Dinar"
            "ils" -> "Shekel"
            "jmd" -> "Jamaican Dollar"
            "jod" -> "Jordanian Dinar"
            "kzt" -> "Tenge"
            "kes" -> "Kenyan Shilling"
            "kpw" -> "Won"
            "krw" -> "Won"
            "kwd" -> "Kuwaiti Dinar"
            "kgs" -> "Som"
            "lak" -> "Kip"
            "lvl" -> "Lat"
            "lbp" -> "Lebanese Pound"
            "lsl" -> "Loti"
            "lrd" -> "Liberian Dollar"
            "lyd" -> "Libyan Dinar"
            "chf" -> "Swiss Franc"
            "ltl" -> "Lita"
            "mop" -> "Pataca"
            "mkd" -> "Denar"
            "mga" -> "Malagasy Franc"
            "mwk" -> "Malawian Kwacha"
            "myr" -> "Ringgit"
            "mvr" -> "Rufiyaa"
            "mtl" -> "Maltese Lira"
            "mro" -> "Ouguiya"
            "mur" -> "Mauritian Rupee"
            "mxn" -> "Peso"
            "mdl" -> "Leu"
            "mnt" -> "Tugrik"
            "mad" -> "Dirham"
            "mzn" -> "Metical"
            "mmk" -> "Kyat"
            "nad" -> "Dollar"
            "npr" -> "Nepalese Rupee"
            "nio" -> "Cordoba Oro"
            "ngn" -> "Naira"
            "omr" -> "Sul Rial"
            "pkr" -> "Rupee"
            "pab" -> "Balboa"
            "pgk" -> "Kina"
            "pyg" -> "Guarani"
            "pen" -> "Nuevo Sol"
            "php" -> "Peso"
            "pln" -> "Zloty"
            "qar" -> "Rial"
            "ron" -> "Leu"
            "rub" -> "Ruble"
            "rwf" -> "Rwanda Franc"
            "std" -> "Dobra"
            "sar" -> "Riyal"
            "scr" -> "Rupee"
            "sll" -> "Leone"
            "sgd" -> "Singapure Dollar"
            "skk" -> "Koruna"
            "sbd" -> "Solomon Islands Dollar"
            "sos" -> "Shilling"
            "zar" -> "Rand"
            "lkr" -> "Rupee"
            "sdg" -> "Dinar"
            "srd" -> "Surinamese Guilder"
            "szl" -> "Lilangeni"
            "sek" -> "Krona"
            "syp" -> "Syrian Pound"
            "twd" -> "Dollar"
            "tjs" -> "Tajikistan Ruble"
            "tzs" -> "Shilling"
            "thb" -> "Baht"
            "top" -> "PaÕanga"
            "ttd" -> "Trinidad and Tobago Dollar"
            "tnd" -> "Tunisian Dinar"
            "try" -> "Lira"
            "tmt" -> "Manat"
            "ugx" -> "Shilling"
            "uah" -> "Hryvnia"
            "aed" -> "Dirham"
            "uyu" -> "Peso"
            "uzs" -> "Som"
            "vuv" -> "Vatu"
            "vef" -> "Bolivar"
            "vnd" -> "Dong"
            "yer" -> "Rial"
            "zmk" -> "Kwacha"
            "zwd" -> "Zimbabwe Dollar"
            "aoa" -> "Angolan kwanza"
            "aqd" -> "Antarctican dollar"
            "bam" -> "Bosnia and Herzegovina convertible mark"
            "ghs" -> "Ghana cedi"
            "ggp" -> "Guernsey pound"
            "rsd" -> "Serbian dinar"
            else -> ""
        }

    data class Model(
        override val listId: String,
        val currencyCode: String,
        val currencyValue: Double
    ) : ListItem

    class ViewHolder(itemView: View) : BaseRecyclerViewHolder(itemView) {
        val image: ImageView = itemView.currency_delegate_image
        val currencyCode: TextView = itemView.currency_delegate_currency_code
        val currencyName: TextView = itemView.currency_delegate_currency_name
        val currencyValue: EditText = itemView.currency_delegate_currency_value
    }

}