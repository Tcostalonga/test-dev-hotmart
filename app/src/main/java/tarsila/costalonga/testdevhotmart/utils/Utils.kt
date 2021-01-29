package tarsila.costalonga.testdevhotmart.utils

import tarsila.costalonga.testdevhotmart.R

const val BASE_URL_LOCATION_API = "https://hotmart-mobile-app.herokuapp.com"
const val BASE_URL_IMAGES_API = "https://pixabay.com/"

fun getRandomColor(): Int {

    return listOf(
        R.drawable.bkg_light_pink,
        R.drawable.bkg_duck_egg_blue,
        R.drawable.bkg_creme
    ).random()

}