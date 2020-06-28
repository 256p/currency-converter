package com.example.currencyconverter.delegate

import android.annotation.SuppressLint
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.example.currencyconverter.R
import com.example.currencyconverter.util.inflate
import com.example.currencyconverter.util.round
import com.revolut.recyclerkit.delegates.BaseRecyclerViewDelegate
import com.revolut.recyclerkit.delegates.BaseRecyclerViewHolder
import com.revolut.recyclerkit.delegates.ListItem
import kotlinx.android.synthetic.main.currency_delegate.view.*
import java.math.BigDecimal
import java.util.*

class CurrencyDelegate(
    private val onClickListener: (Model, Int) -> Unit,
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

    @SuppressLint("ClickableViewAccessibility")
    private fun ViewHolder.applyData(data: Model) {
        val context = itemView.context
        itemView.setOnClickListener { onClickListener(data, adapterPosition) }
        currencyValue.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP && adapterPosition != 0) {
                onClickListener(data, adapterPosition)
            }
            false
        }
        currencyValue.addTextChangedListener {
            if (adapterPosition == 0) onValueChangedListener(it.toString())
        }
        image.loadImage(getCountryFlagByCode(context, data.currencyCode))
        currencyCode.text = data.currencyCode
        currencyName.text = getCurrencyNameByCode(context, data.currencyCode)
        if (data.currencyValue >= Float.MAX_VALUE || data.currencyValue.isNaN() || data.currencyValue.isInfinite()) {
            Toast.makeText(itemView.context, context.getString(R.string.input_value_is_too_big), Toast.LENGTH_SHORT).show()
            currencyValue.setText(context.getString(R.string.error))
        } else {
            currencyValue.setText(
                BigDecimal(
                    data.currencyValue.round(3).toString()
                ).toPlainString()
            )
        }
    }

    private fun ImageView.loadImage(@DrawableRes imageRes: Int) =
        Glide.with(this)
            .load(imageRes)
            .circleCrop()
            .into(this)

    //currency code according to ISO 4217
    private fun getCountryFlagByCode(context: Context, currencyCode: String): Int =
        when (currencyCode.toLowerCase(Locale.getDefault())) {
            context.getString(R.string.currency_code_nzd) -> R.drawable.nz
            context.getString(R.string.currency_code_aud) -> R.drawable.au
            context.getString(R.string.currency_code_eur) -> R.drawable.eu
            context.getString(R.string.currency_code_gbp) -> R.drawable.gb
            context.getString(R.string.currency_code_usd) -> R.drawable.us
            context.getString(R.string.currency_code_hkd) -> R.drawable.hk
            context.getString(R.string.currency_code_cad) -> R.drawable.ca
            context.getString(R.string.currency_code_jpy) -> R.drawable.jp
            context.getString(R.string.currency_code_afn) -> R.drawable.af
            context.getString(R.string.currency_code_all) -> R.drawable.al
            context.getString(R.string.currency_code_dzd) -> R.drawable.dz
            context.getString(R.string.currency_code_xcd) -> R.drawable.ag
            context.getString(R.string.currency_code_ars) -> R.drawable.ar
            context.getString(R.string.currency_code_amd) -> R.drawable.am
            context.getString(R.string.currency_code_ang) -> R.drawable.an
            context.getString(R.string.currency_code_azn) -> R.drawable.az
            context.getString(R.string.currency_code_bsd) -> R.drawable.bs
            context.getString(R.string.currency_code_bhd) -> R.drawable.bh
            context.getString(R.string.currency_code_bdt) -> R.drawable.bd
            context.getString(R.string.currency_code_bbd) -> R.drawable.bb
            context.getString(R.string.currency_code_byr) -> R.drawable.by
            context.getString(R.string.currency_code_bzd) -> R.drawable.bz
            context.getString(R.string.currency_code_xof) -> R.drawable.sn
            context.getString(R.string.currency_code_bmd) -> R.drawable.bm
            context.getString(R.string.currency_code_inr) -> R.drawable.`in`
            context.getString(R.string.currency_code_bob) -> R.drawable.bo
            context.getString(R.string.currency_code_bwp) -> R.drawable.bw
            context.getString(R.string.currency_code_nok) -> R.drawable.no
            context.getString(R.string.currency_code_brl) -> R.drawable.br
            context.getString(R.string.currency_code_bnd) -> R.drawable.bn
            context.getString(R.string.currency_code_bgn) -> R.drawable.bg
            context.getString(R.string.currency_code_bif) -> R.drawable.bi
            context.getString(R.string.currency_code_khr) -> R.drawable.kh
            context.getString(R.string.currency_code_xaf) -> R.drawable.cm
            context.getString(R.string.currency_code_cve) -> R.drawable.cv
            context.getString(R.string.currency_code_kyd) -> R.drawable.ky
            context.getString(R.string.currency_code_clp) -> R.drawable.cl
            context.getString(R.string.currency_code_cny) -> R.drawable.cn
            context.getString(R.string.currency_code_cop) -> R.drawable.co
            context.getString(R.string.currency_code_kmf) -> R.drawable.km
            context.getString(R.string.currency_code_cdf) -> R.drawable.cd
            context.getString(R.string.currency_code_crc) -> R.drawable.cr
            context.getString(R.string.currency_code_hrk) -> R.drawable.hr
            context.getString(R.string.currency_code_cup) -> R.drawable.cu
            context.getString(R.string.currency_code_cyp) -> R.drawable.cy
            context.getString(R.string.currency_code_czk) -> R.drawable.cz
            context.getString(R.string.currency_code_dkk) -> R.drawable.dk
            context.getString(R.string.currency_code_djf) -> R.drawable.dj
            context.getString(R.string.currency_code_dop) -> R.drawable._do
            context.getString(R.string.currency_code_idr) -> R.drawable.id
            context.getString(R.string.currency_code_ecs) -> R.drawable.ec
            context.getString(R.string.currency_code_egp) -> R.drawable.eg
            context.getString(R.string.currency_code_svc) -> R.drawable.sv
            context.getString(R.string.currency_code_etb) -> R.drawable.et
            context.getString(R.string.currency_code_eek) -> R.drawable.ee
            context.getString(R.string.currency_code_fkp) -> R.drawable.fk
            context.getString(R.string.currency_code_fjd) -> R.drawable.fj
            context.getString(R.string.currency_code_xpf) -> R.drawable.pf
            context.getString(R.string.currency_code_gmd) -> R.drawable.gm
            context.getString(R.string.currency_code_gel) -> R.drawable.ge
            context.getString(R.string.currency_code_gip) -> R.drawable.gi
            context.getString(R.string.currency_code_gtq) -> R.drawable.gt
            context.getString(R.string.currency_code_gnf) -> R.drawable.gn
            context.getString(R.string.currency_code_gyd) -> R.drawable.gy
            context.getString(R.string.currency_code_htg) -> R.drawable.ht
            context.getString(R.string.currency_code_hnl) -> R.drawable.hn
            context.getString(R.string.currency_code_huf) -> R.drawable.hu
            context.getString(R.string.currency_code_isk) -> R.drawable.`is`
            context.getString(R.string.currency_code_irr) -> R.drawable.ir
            context.getString(R.string.currency_code_iqd) -> R.drawable.iq
            context.getString(R.string.currency_code_ils) -> R.drawable.il
            context.getString(R.string.currency_code_jmd) -> R.drawable.jm
            context.getString(R.string.currency_code_jod) -> R.drawable.jo
            context.getString(R.string.currency_code_kzt) -> R.drawable.kz
            context.getString(R.string.currency_code_kes) -> R.drawable.ke
            context.getString(R.string.currency_code_kpw) -> R.drawable.kp
            context.getString(R.string.currency_code_krw) -> R.drawable.kr
            context.getString(R.string.currency_code_kwd) -> R.drawable.kw
            context.getString(R.string.currency_code_kgs) -> R.drawable.kg
            context.getString(R.string.currency_code_lvl) -> R.drawable.lv
            context.getString(R.string.currency_code_lbp) -> R.drawable.lb
            context.getString(R.string.currency_code_lsl) -> R.drawable.ls
            context.getString(R.string.currency_code_lrd) -> R.drawable.lr
            context.getString(R.string.currency_code_lyd) -> R.drawable.ly
            context.getString(R.string.currency_code_chf) -> R.drawable.ch
            context.getString(R.string.currency_code_ltl) -> R.drawable.lt
            context.getString(R.string.currency_code_mop) -> R.drawable.mo
            context.getString(R.string.currency_code_mkd) -> R.drawable.mk
            context.getString(R.string.currency_code_mga) -> R.drawable.mg
            context.getString(R.string.currency_code_mwk) -> R.drawable.mw
            context.getString(R.string.currency_code_myr) -> R.drawable.my
            context.getString(R.string.currency_code_mvr) -> R.drawable.mv
            context.getString(R.string.currency_code_mtl) -> R.drawable.mt
            context.getString(R.string.currency_code_mro) -> R.drawable.mr
            context.getString(R.string.currency_code_mur) -> R.drawable.mu
            context.getString(R.string.currency_code_mxn) -> R.drawable.mx
            context.getString(R.string.currency_code_mdl) -> R.drawable.md
            context.getString(R.string.currency_code_mnt) -> R.drawable.mn
            context.getString(R.string.currency_code_mad) -> R.drawable.ma
            context.getString(R.string.currency_code_mzn) -> R.drawable.mz
            context.getString(R.string.currency_code_mmk) -> R.drawable.mm
            context.getString(R.string.currency_code_nad) -> R.drawable.na
            context.getString(R.string.currency_code_npr) -> R.drawable.np
            context.getString(R.string.currency_code_nio) -> R.drawable.ni
            context.getString(R.string.currency_code_ngn) -> R.drawable.ng
            context.getString(R.string.currency_code_omr) -> R.drawable.om
            context.getString(R.string.currency_code_pkr) -> R.drawable.pk
            context.getString(R.string.currency_code_pab) -> R.drawable.pa
            context.getString(R.string.currency_code_pgk) -> R.drawable.pg
            context.getString(R.string.currency_code_pyg) -> R.drawable.py
            context.getString(R.string.currency_code_pen) -> R.drawable.pe
            context.getString(R.string.currency_code_php) -> R.drawable.ph
            context.getString(R.string.currency_code_pln) -> R.drawable.pl
            context.getString(R.string.currency_code_qar) -> R.drawable.qa
            context.getString(R.string.currency_code_ron) -> R.drawable.ro
            context.getString(R.string.currency_code_rub) -> R.drawable.ru
            context.getString(R.string.currency_code_rwf) -> R.drawable.rw
            context.getString(R.string.currency_code_std) -> R.drawable.st
            context.getString(R.string.currency_code_sar) -> R.drawable.sa
            context.getString(R.string.currency_code_scr) -> R.drawable.sc
            context.getString(R.string.currency_code_sll) -> R.drawable.sl
            context.getString(R.string.currency_code_sgd) -> R.drawable.sg
            context.getString(R.string.currency_code_skk) -> R.drawable.sk
            context.getString(R.string.currency_code_sbd) -> R.drawable.sb
            context.getString(R.string.currency_code_sos) -> R.drawable.so
            context.getString(R.string.currency_code_zar) -> R.drawable.za
            context.getString(R.string.currency_code_lkr) -> R.drawable.lk
            context.getString(R.string.currency_code_sdg) -> R.drawable.sd
            context.getString(R.string.currency_code_srd) -> R.drawable.sr
            context.getString(R.string.currency_code_szl) -> R.drawable.sz
            context.getString(R.string.currency_code_sek) -> R.drawable.se
            context.getString(R.string.currency_code_syp) -> R.drawable.sy
            context.getString(R.string.currency_code_twd) -> R.drawable.tw
            context.getString(R.string.currency_code_tjs) -> R.drawable.tj
            context.getString(R.string.currency_code_tzs) -> R.drawable.tz
            context.getString(R.string.currency_code_thb) -> R.drawable.th
            context.getString(R.string.currency_code_top) -> R.drawable.to
            context.getString(R.string.currency_code_ttd) -> R.drawable.tt
            context.getString(R.string.currency_code_tnd) -> R.drawable.tn
            context.getString(R.string.currency_code_try) -> R.drawable.tr
            context.getString(R.string.currency_code_tmt) -> R.drawable.tm
            context.getString(R.string.currency_code_ugx) -> R.drawable.ug
            context.getString(R.string.currency_code_uah) -> R.drawable.ua
            context.getString(R.string.currency_code_aed) -> R.drawable.ae
            context.getString(R.string.currency_code_uyu) -> R.drawable.uy
            context.getString(R.string.currency_code_uzs) -> R.drawable.uz
            context.getString(R.string.currency_code_vuv) -> R.drawable.vu
            context.getString(R.string.currency_code_vef) -> R.drawable.ve
            context.getString(R.string.currency_code_vnd) -> R.drawable.vn
            context.getString(R.string.currency_code_yer) -> R.drawable.ye
            context.getString(R.string.currency_code_zmk) -> R.drawable.zm
            context.getString(R.string.currency_code_zwd) -> R.drawable.zw
            context.getString(R.string.currency_code_aoa) -> R.drawable.ao
            context.getString(R.string.currency_code_aqd) -> R.drawable.aq
            context.getString(R.string.currency_code_bam) -> R.drawable.ba
            context.getString(R.string.currency_code_ghs) -> R.drawable.gh
            context.getString(R.string.currency_code_ggp) -> R.drawable.gg
            context.getString(R.string.currency_code_lak) -> R.drawable.la
            context.getString(R.string.currency_code_rsd) -> R.drawable.rs
            else -> -1
        }

    private fun getCurrencyNameByCode(context: Context, currencyCode: String): String =
        when (currencyCode.toLowerCase(Locale.getDefault())) {
            context.getString(R.string.currency_code_nzd) -> context.getString(R.string.new_zealand_dollar)
            context.getString(R.string.currency_code_aud) -> context.getString(R.string.australian_dollar)
            context.getString(R.string.currency_code_eur) -> context.getString(R.string.euro)
            context.getString(R.string.currency_code_gbp) -> context.getString(R.string.sterling)
            context.getString(R.string.currency_code_usd) -> context.getString(R.string.us_dollar)
            context.getString(R.string.currency_code_hkd) -> context.getString(R.string.hong_kong_dollar)
            context.getString(R.string.currency_code_cad) -> context.getString(R.string.canadian_dollar)
            context.getString(R.string.currency_code_jpy) -> context.getString(R.string.japanese_yen)
            context.getString(R.string.currency_code_afn) -> context.getString(R.string.afghani)
            context.getString(R.string.currency_code_all) -> context.getString(R.string.lek)
            context.getString(R.string.currency_code_dzd) -> context.getString(R.string.algerian_dinar)
            context.getString(R.string.currency_code_xcd) -> context.getString(R.string.east_caribbean_dollar)
            context.getString(R.string.currency_code_ars) -> context.getString(R.string.peso)
            context.getString(R.string.currency_code_amd) -> context.getString(R.string.dram)
            context.getString(R.string.currency_code_ang) -> context.getString(R.string.netherlands_antilles_guilder)
            context.getString(R.string.currency_code_azn) -> context.getString(R.string.manat)
            context.getString(R.string.currency_code_bsd) -> context.getString(R.string.bahamian_dollar)
            context.getString(R.string.currency_code_bhd) -> context.getString(R.string.bahraini_dinar)
            context.getString(R.string.currency_code_bdt) -> context.getString(R.string.taka)
            context.getString(R.string.currency_code_bbd) -> context.getString(R.string.barbadian_dollar)
            context.getString(R.string.currency_code_byr) -> context.getString(R.string.belarus_ruble)
            context.getString(R.string.currency_code_bzd) -> context.getString(R.string.belizean_dollar)
            context.getString(R.string.currency_code_xof) -> context.getString(R.string.cfa_franc_bceao)
            context.getString(R.string.currency_code_bmd) -> context.getString(R.string.bermudian_dollar)
            context.getString(R.string.currency_code_inr) -> context.getString(R.string.indian_rupee)
            context.getString(R.string.currency_code_bob) -> context.getString(R.string.boliviano)
            context.getString(R.string.currency_code_bwp) -> context.getString(R.string.pula)
            context.getString(R.string.currency_code_nok) -> context.getString(R.string.norwegian_krone)
            context.getString(R.string.currency_code_brl) -> context.getString(R.string.brazil)
            context.getString(R.string.currency_code_bnd) -> context.getString(R.string.bruneian_dollar)
            context.getString(R.string.currency_code_bgn) -> context.getString(R.string.lev)
            context.getString(R.string.currency_code_bif) -> context.getString(R.string.burundi_franc)
            context.getString(R.string.currency_code_khr) -> context.getString(R.string.riel)
            context.getString(R.string.currency_code_xaf) -> context.getString(R.string.cfa_franc_beac)
            context.getString(R.string.currency_code_cve) -> context.getString(R.string.escudo)
            context.getString(R.string.currency_code_kyd) -> context.getString(R.string.caymanian_dollar)
            context.getString(R.string.currency_code_clp) -> context.getString(R.string.chilean_peso)
            context.getString(R.string.currency_code_cny) -> context.getString(R.string.yuan_renminbi)
            context.getString(R.string.currency_code_cop) -> context.getString(R.string.peso)
            context.getString(R.string.currency_code_kmf) -> context.getString(R.string.comoran_franc)
            context.getString(R.string.currency_code_cdf) -> context.getString(R.string.congolese_frank)
            context.getString(R.string.currency_code_crc) -> context.getString(R.string.costa_rican_colon)
            context.getString(R.string.currency_code_hrk) -> context.getString(R.string.croatian_dinar)
            context.getString(R.string.currency_code_cup) -> context.getString(R.string.cuban_peso)
            context.getString(R.string.currency_code_cyp) -> context.getString(R.string.cypriot_pound)
            context.getString(R.string.currency_code_czk) -> context.getString(R.string.koruna)
            context.getString(R.string.currency_code_dkk) -> context.getString(R.string.danish_krone)
            context.getString(R.string.currency_code_djf) -> context.getString(R.string.djiboutian_franc)
            context.getString(R.string.currency_code_dop) -> context.getString(R.string.dominican_peso)
            context.getString(R.string.currency_code_idr) -> context.getString(R.string.indonesian_rupiah)
            context.getString(R.string.currency_code_ecs) -> context.getString(R.string.sucre)
            context.getString(R.string.currency_code_egp) -> context.getString(R.string.egyptian_pound)
            context.getString(R.string.currency_code_svc) -> context.getString(R.string.salvadoran_colon)
            context.getString(R.string.currency_code_etb) -> context.getString(R.string.ethiopian_birr)
            context.getString(R.string.currency_code_eek) -> context.getString(R.string.estonian_kroon)
            context.getString(R.string.currency_code_fkp) -> context.getString(R.string.falkland_pound)
            context.getString(R.string.currency_code_fjd) -> context.getString(R.string.fijian_dollar)
            context.getString(R.string.currency_code_xpf) -> context.getString(R.string.cfp_franc)
            context.getString(R.string.currency_code_gmd) -> context.getString(R.string.dalasi)
            context.getString(R.string.currency_code_gel) -> context.getString(R.string.lari)
            context.getString(R.string.currency_code_gip) -> context.getString(R.string.gibraltar_pound)
            context.getString(R.string.currency_code_gtq) -> context.getString(R.string.quetzal)
            context.getString(R.string.currency_code_gnf) -> context.getString(R.string.guinean_franc)
            context.getString(R.string.currency_code_gyd) -> context.getString(R.string.guyanaese_dollar)
            context.getString(R.string.currency_code_htg) -> context.getString(R.string.gourde)
            context.getString(R.string.currency_code_hnl) -> context.getString(R.string.lempira)
            context.getString(R.string.currency_code_huf) -> context.getString(R.string.forint)
            context.getString(R.string.currency_code_isk) -> context.getString(R.string.icelandic_krona)
            context.getString(R.string.currency_code_irr) -> context.getString(R.string.iranian_rial)
            context.getString(R.string.currency_code_iqd) -> context.getString(R.string.iraqi_dinar)
            context.getString(R.string.currency_code_ils) -> context.getString(R.string.shekel)
            context.getString(R.string.currency_code_jmd) -> context.getString(R.string.jamaican_dollar)
            context.getString(R.string.currency_code_jod) -> context.getString(R.string.jordanian_dinar)
            context.getString(R.string.currency_code_kzt) -> context.getString(R.string.tenge)
            context.getString(R.string.currency_code_kes) -> context.getString(R.string.kenyan_shilling)
            context.getString(R.string.currency_code_kpw) -> context.getString(R.string.won)
            context.getString(R.string.currency_code_krw) -> context.getString(R.string.won)
            context.getString(R.string.currency_code_kwd) -> context.getString(R.string.kuwaiti_dinar)
            context.getString(R.string.currency_code_kgs) -> context.getString(R.string.som)
            context.getString(R.string.currency_code_lak) -> context.getString(R.string.kip)
            context.getString(R.string.currency_code_lvl) -> context.getString(R.string.lat)
            context.getString(R.string.currency_code_lbp) -> context.getString(R.string.lebanese_pound)
            context.getString(R.string.currency_code_lsl) -> context.getString(R.string.loti)
            context.getString(R.string.currency_code_lrd) -> context.getString(R.string.liberian_dollar)
            context.getString(R.string.currency_code_lyd) -> context.getString(R.string.libyan_dinar)
            context.getString(R.string.currency_code_chf) -> context.getString(R.string.swiss_franc)
            context.getString(R.string.currency_code_ltl) -> context.getString(R.string.lita)
            context.getString(R.string.currency_code_mop) -> context.getString(R.string.pataca)
            context.getString(R.string.currency_code_mkd) -> context.getString(R.string.denar)
            context.getString(R.string.currency_code_mga) -> context.getString(R.string.malagasy_franc)
            context.getString(R.string.currency_code_mwk) -> context.getString(R.string.malawian_kwacha)
            context.getString(R.string.currency_code_myr) -> context.getString(R.string.ringgit)
            context.getString(R.string.currency_code_mvr) -> context.getString(R.string.rufiyaa)
            context.getString(R.string.currency_code_mtl) -> context.getString(R.string.maltese_lira)
            context.getString(R.string.currency_code_mro) -> context.getString(R.string.ouguiya)
            context.getString(R.string.currency_code_mur) -> context.getString(R.string.mauritian_rupee)
            context.getString(R.string.currency_code_mxn) -> context.getString(R.string.peso)
            context.getString(R.string.currency_code_mdl) -> context.getString(R.string.leu)
            context.getString(R.string.currency_code_mnt) -> context.getString(R.string.tugrik)
            context.getString(R.string.currency_code_mad) -> context.getString(R.string.dirham)
            context.getString(R.string.currency_code_mzn) -> context.getString(R.string.metical)
            context.getString(R.string.currency_code_mmk) -> context.getString(R.string.kyat)
            context.getString(R.string.currency_code_nad) -> context.getString(R.string.dollar)
            context.getString(R.string.currency_code_npr) -> context.getString(R.string.nepalese_rupee)
            context.getString(R.string.currency_code_nio) -> context.getString(R.string.cordoba_oro)
            context.getString(R.string.currency_code_ngn) -> context.getString(R.string.naira)
            context.getString(R.string.currency_code_omr) -> context.getString(R.string.sul_rial)
            context.getString(R.string.currency_code_pkr) -> context.getString(R.string.rupee)
            context.getString(R.string.currency_code_pab) -> context.getString(R.string.balboa)
            context.getString(R.string.currency_code_pgk) -> context.getString(R.string.kina)
            context.getString(R.string.currency_code_pyg) -> context.getString(R.string.guarani)
            context.getString(R.string.currency_code_pen) -> context.getString(R.string.nuevo_sol)
            context.getString(R.string.currency_code_php) -> context.getString(R.string.peso)
            context.getString(R.string.currency_code_pln) -> context.getString(R.string.zloty)
            context.getString(R.string.currency_code_qar) -> context.getString(R.string.rial)
            context.getString(R.string.currency_code_ron) -> context.getString(R.string.leu)
            context.getString(R.string.currency_code_rub) -> context.getString(R.string.ruble)
            context.getString(R.string.currency_code_rwf) -> context.getString(R.string.rwanda_franc)
            context.getString(R.string.currency_code_std) -> context.getString(R.string.dobra)
            context.getString(R.string.currency_code_sar) -> context.getString(R.string.riyal)
            context.getString(R.string.currency_code_scr) -> context.getString(R.string.rupee)
            context.getString(R.string.currency_code_sll) -> context.getString(R.string.leone)
            context.getString(R.string.currency_code_sgd) -> context.getString(R.string.singapure_dollar)
            context.getString(R.string.currency_code_skk) -> context.getString(R.string.koruna)
            context.getString(R.string.currency_code_sbd) -> context.getString(R.string.solomon_islands_dollar)
            context.getString(R.string.currency_code_sos) -> context.getString(R.string.shilling)
            context.getString(R.string.currency_code_zar) -> context.getString(R.string.rand)
            context.getString(R.string.currency_code_lkr) -> context.getString(R.string.rupee)
            context.getString(R.string.currency_code_sdg) -> context.getString(R.string.dinar)
            context.getString(R.string.currency_code_srd) -> context.getString(R.string.surinamese_guilder)
            context.getString(R.string.currency_code_szl) -> context.getString(R.string.lilangeni)
            context.getString(R.string.currency_code_sek) -> context.getString(R.string.krona)
            context.getString(R.string.currency_code_syp) -> context.getString(R.string.syrian_pound)
            context.getString(R.string.currency_code_twd) -> context.getString(R.string.dollar)
            context.getString(R.string.currency_code_tjs) -> context.getString(R.string.tajikistan_ruble)
            context.getString(R.string.currency_code_tzs) -> context.getString(R.string.shilling)
            context.getString(R.string.currency_code_thb) -> context.getString(R.string.baht)
            context.getString(R.string.currency_code_top) -> context.getString(R.string.paõanga)
            context.getString(R.string.currency_code_ttd) -> context.getString(R.string.trinidad_and_tobago_dollar)
            context.getString(R.string.currency_code_tnd) -> context.getString(R.string.tunisian_dinar)
            context.getString(R.string.currency_code_try) -> context.getString(R.string.lira)
            context.getString(R.string.currency_code_tmt) -> context.getString(R.string.manat)
            context.getString(R.string.currency_code_ugx) -> context.getString(R.string.shilling)
            context.getString(R.string.currency_code_uah) -> context.getString(R.string.hryvnia)
            context.getString(R.string.currency_code_aed) -> context.getString(R.string.dirham)
            context.getString(R.string.currency_code_uyu) -> context.getString(R.string.peso)
            context.getString(R.string.currency_code_uzs) -> context.getString(R.string.som)
            context.getString(R.string.currency_code_vuv) -> context.getString(R.string.vatu)
            context.getString(R.string.currency_code_vef) -> context.getString(R.string.bolivar)
            context.getString(R.string.currency_code_vnd) -> context.getString(R.string.dong)
            context.getString(R.string.currency_code_yer) -> context.getString(R.string.rial)
            context.getString(R.string.currency_code_zmk) -> context.getString(R.string.kwacha)
            context.getString(R.string.currency_code_zwd) -> context.getString(R.string.zimbabwe_dollar)
            context.getString(R.string.currency_code_aoa) -> context.getString(R.string.angolan_kwanza)
            context.getString(R.string.currency_code_aqd) -> context.getString(R.string.antarctican_dollar)
            context.getString(R.string.currency_code_bam) -> context.getString(R.string.bosnia_and_herzegovina_convertible_mark)
            context.getString(R.string.currency_code_ghs) -> context.getString(R.string.ghana_cedi)
            context.getString(R.string.currency_code_ggp) -> context.getString(R.string.guernsey_pound)
            context.getString(R.string.currency_code_rsd) -> context.getString(R.string.serbian_dinar)
            else -> ""
        }

    data class Model(
        override val listId: String,
        val currencyCode: String,
        var currencyValue: Float
    ) : ListItem

    class ViewHolder(itemView: View) : BaseRecyclerViewHolder(itemView) {
        val image: ImageView = itemView.currency_delegate_image
        val currencyCode: TextView = itemView.currency_delegate_currency_code
        val currencyName: TextView = itemView.currency_delegate_currency_name
        val currencyValue: EditText = itemView.currency_delegate_currency_value
    }

}