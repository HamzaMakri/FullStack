<template>
  <div class="home">
    <h2>Les villes</h2>

              <div class="form-group">
            <label for="country">Trier Par Pays:</label>
            <select id="countrySelector" class="form-control" v-model="data.editedCity.country" @change="orderByCountry()">
              <option disabled value="0">Choisissez un pays</option>
              <option
                v-for="country in data.allCountries"
                :key="country.id"
                :value="country.id"
              >
                {{ country.name }}
              </option>
            </select>
          </div>
          <br>

    <city-list v-bind:cities="data.allCities" @refresh="fetchCities()" />
  </div>
</template>

<script setup>
import { reactive, onMounted } from "vue";
// @ is an alias to /src
import CityList from "@/components/CityList.vue";

const emptyCity = {
  id: "",
  name: "",
  population: 1,
  country: "",
};

const data = reactive({
  allCities: [],
  allCountries: [],
  editedCity: { ...emptyCity },
});

function orderByCountry(){
    let selectedCountryId = document.getElementById('countrySelector').value;
  fetch("api/citiesByCountry?id=" + selectedCountryId)
    .then((response) => response.json())
    .then((json) => {
      data.allCities = json
    })
    .catch((error) => alert(error));
}

function fetchCities() {
  // Utilise l'API ad-hoc pour avoir le pays de chaque ville
  fetch("api/allCities")
    .then((response) => response.json())
    .then((json) => {
        console.log(json);
      data.allCities = json;
    })
    .catch((error) => alert(error));
}

// Utilise l'API REST auto-générée pour avoir les pays
function fetchCountries() {
  fetch("api/countries")
    .then((response) => response.json())
    .then((json) => {
      data.allCountries = json._embedded.countries;
    })
    .catch((error) => alert(error));
}

function saveCity() {
  //var data = new URLSearchParams(this.editedCity).toString();
  ajaxSaveCity()
    .then((json) => {
      data.editedCity = { ...emptyCity };
      fetchCities(); // On rafraîchit les villes
})
    .catch((error) => alert(error));
}

async function ajaxSaveCity() {
  const options = {
    method: "POST",
    mode: "cors",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data.editedCity),
  };
  // Utilise l'API REST auto-générée pour enregistrer la ville
  const response = await fetch("api/cities", options);
  if (!response.ok) {
    // status != 2XX
    const message = await response.text();
    throw new Error(message);
  }
  return response.json();
}

// Au chargement du composant
onMounted(() => {
  fetchCities(); // On récupère les villes (pour la table)
  fetchCountries(); // On récupère les pays (pour le sélecteur de pays)
});
</script>
