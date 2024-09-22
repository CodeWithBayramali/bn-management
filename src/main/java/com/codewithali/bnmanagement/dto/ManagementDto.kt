package com.codewithali.bnmanagement.dto

import com.codewithali.bnmanagement.model.Management
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class ManagementDto @JvmOverloads constructor(
    @JsonProperty("caseNumber")
    val caseNumber: Int,
    @JsonProperty("operatorAdi")
    val operatorAdi: String,
    @JsonProperty("firmaAdi")
    val firmaAdi: String,
    @JsonProperty("vakaIlcesi")
    val vakaIlcesi: String,
    @JsonProperty("vakaSehri")
    val vakaSehri: String,
    @JsonProperty("hizmet")
    val hizmet: String,
    @JsonProperty("mesafe")
    val mesafe: Int,
    @JsonProperty("ekPoz")
    val ekPoz: String,
    @JsonProperty("date")
    val date: LocalDate,
    @JsonProperty("ilceler")
    val ilceler: String,
    @JsonProperty("pozSayisi")
    val pozSayisi: Int,
    @JsonProperty("vakaSonlandiran")
    val vakaSonlandiran: String,
    @JsonProperty("aciklama")
    val aciklama: String,
    val documentUrl: String? = null,
    val id: String? = null,
) {
    companion object {
        @JvmStatic
        fun convertToDto(from: Management): ManagementDto  {
            return ManagementDto(
                from.caseNumber,
                from.operatorAdi,
                from.firmaAdi,
                from.vakaIlcesi,
                from.vakaSehri,
                from.hizmet,
                from.mesafe,
                from.ekPoz,
                from.date,
                from.ilceler,
                from.pozSayisi,
                from.vakaSonlandiran,
                from.aciklama,
                from.documentUrl,
                from.id
            )
        }
    }
}
