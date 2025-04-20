package com.android.practise.entity

import kotlinx.serialization.Serializable

@Serializable
data class GeoObjectCollectionResponse(
    val response: Response
)

@Serializable
data class Response(
    val GeoObjectCollection: GeoObjectCollection
)

@Serializable
data class GeoObjectCollection(
    val metaDataProperty: MetaDataProperty,
    val featureMember: List<FeatureMember>
)

@Serializable
data class MetaDataProperty(
    val GeocoderResponseMetaData: GeocoderResponseMetaData
)

@Serializable
data class GeocoderResponseMetaData(
    val request: String,
    val found: String,
    val results: String
)

@Serializable
data class FeatureMember(
    val GeoObject: GeoObject
)

@Serializable
data class GeoObject(
    val metaDataProperty: GeoObjectMetaDataProperty,
    val description: String? = null,
    val name: String,
    val boundedBy: BoundedBy,
    val Point: Point
)

@Serializable
data class GeoObjectMetaDataProperty(
    val GeocoderMetaData: GeocoderMetaData
)

@Serializable
data class GeocoderMetaData(
    val kind: String,
    val text: String,
    val precision: String,
    val Address: Address,
    val AddressDetails: AddressDetails
)

@Serializable
data class Address(
    val country_code: String? = null,
    val formatted: String,
    val Components: List<Component>
)

@Serializable
data class Component(
    val kind: String,
    val name: String
)

@Serializable
data class AddressDetails(
    val Country: Country? = null
)

@Serializable
data class Country(
    val AddressLine: String,
    val CountryNameCode: String,
    val CountryName: String,
    val AdministrativeArea: AdministrativeArea? = null
)

@Serializable
data class AdministrativeArea(
    val AdministrativeAreaName: String,
    val SubAdministrativeArea: SubAdministrativeArea? = null
)

@Serializable
data class SubAdministrativeArea(
    val SubAdministrativeAreaName: String,
)

@Serializable
data class Thoroughfare(
    val ThoroughfareName: String,
    val Premise: Premise
)

@Serializable
data class Premise(
    val PremiseNumber: String,
    val PostalCode: PostalCode
)

@Serializable
data class PostalCode(
    val PostalCodeNumber: String
)

@Serializable
data class BoundedBy(
    val Envelope: Envelope
)

@Serializable
data class Envelope(
    val lowerCorner: String,
    val upperCorner: String
)

@Serializable
data class Point(
    val pos: String
)