<template>
  <div class="container">
    <div class="row">
      <div class="col-md-12">
        <h1 class="text-center">Select Language and Proficiency</h1>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12 text-center mb-3">
        <select v-model="selectedLanguage" @change="onLanguageChange">
          <option disabled value="">Select a Language</option>
          <option
            v-for="language in languages"
            :key="language"
            :value="language"
          >
            {{ language }}
          </option>
        </select>
      </div>
    </div>

    <div class="row" v-if="selectedLanguage">
      <div class="col-md-12 text-center mb-3">
        <select v-model="selectedProficiency">
          <option disabled value="">Select Proficiency Level</option>
          <option
            v-for="level in proficiencyLevels"
            :key="level"
            :value="level"
          >
            {{ level }}
          </option>
        </select>
      </div>
    </div>

    <div class="row" v-if="selectedLanguage && selectedProficiency">
      <div class="col-md-12 text-center">
        <h3>You have selected:</h3>
        <p><strong>Language:</strong> {{ selectedLanguage }}</p>
        <p><strong>Proficiency Level:</strong> {{ selectedProficiency }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

const languages = ["English", "Spanish", "French", "German", "Italian"];
const proficiencyLevels = ["Beginner", "Intermediate", "Advanced"];

const selectedLanguage = ref("");
const selectedProficiency = ref("");

const onLanguageChange = () => {
  // Reset the selected proficiency when the language changes
  selectedProficiency.value = "";
};
</script>

<style scoped>
.container {
  margin-top: 20px;
}

select {
  padding: 10px;
  font-size: 16px;
  width: 200px;
}

h3 {
  margin-top: 20px;
}
</style>
