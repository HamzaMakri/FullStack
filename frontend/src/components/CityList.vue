<template>
  <div>
    <table class="table table-bordered table-sm table-hover">
      <thead>
        <tr>
          <th>Nom</th>
          <th>Population</th>
          <th>Pays</th>
          <th>Action</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="city in cities" :key="city.id">
          <td>{{ city.name }}</td>
          <td>{{ city.population }}</td>
          <td>{{ city.country.name }}</td>
          <td>
            <button
              class="btn btn-sm btn-outline-danger"
              @click="deleteCity(city)"
            >
              Supprimer
            </button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>

const emit = defineEmits(['refresh',])

const props = defineProps({
  cities: {
    type: Array,
    required: true,
  },
});


function deleteCity(city) {
  console.log(city);
  const options = {
    method: "DELETE",
  };
  fetch("api/cities/" + city.id, options)
    .then((response) => {
      if (!response.ok) { // status != 2XX
        throw new Error(response.status);
      }
   })
    .then((json) => {
      emit('refresh', json); // On notifie le parent que le pays a été ajouté
    })    
    .catch((error) => alert(error));  
}


</script>
