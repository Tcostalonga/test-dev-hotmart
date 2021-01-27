package tarsila.costalonga.testdevhotmart.utils

import tarsila.costalonga.testdevhotmart.R

const val BASE_URL_LOCATION_API = "https://hotmart-mobile-app.herokuapp.com"
const val BASE_URL_IMAGES_API = "https://pixabay.com/"

const val EMPTY_INVALID_REQUEST = "Desculpe, não há informação disponível no momento"
const val NOT_FOUND_REQUEST = "Ocorreu um erro inesperado, tente novamente"
const val NOT_CONNECTED_REQUEST = "Verifique sua conexão"


fun getRandomColor(): Int {

    val listOfColors = listOf(
        R.drawable.bkg_light_pink,
        R.drawable.bkg_duck_egg_blue,
        R.drawable.bkg_creme
    )

    return listOfColors.random()

}