<template>
  <div class="background-image">
    <img
      src="../../assets/LangLearn.jpeg"
      alt="LangLearn Background"
      class="bg-image"
    />
    <div class="overlay"></div>
  </div>
  <div v-if="showEditMode" class="container">
    <div class="card">
      <div class="card-header">
        <h5 class="card-title text-center">Select Language and Proficiency</h5>
      </div>
      <div class="card-body">
        <div class="row">
          <div class="col-md-12 text-center mb-3">
            <select v-model="selectedLanguage" @change="onLanguageChange">
              <option disabled value="">Select a Language</option>
              <option
                v-for="language in languages"
                :key="language"
                :value="language.id"
              >
                {{ language.name }}
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

          <div class="col-md-12 text-center">
            <div class="d-flex justify-content-center">
              <a
                href="#"
                v-if="showSaveButton && selectedProficiency && selectedLanguage"
                class="btn btn-primary"
                @click="onSavePreference"
                >Save</a
              >
              <a href="#" class="btn btn-secondary" @click="onCancelButton"
                >Cancel</a
              >
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div v-else class="container">
    <div v-if="selectedLanguage && selectedProficiency" class="card">
      <div class="card-body">
        <h5 class="card-title">You have selected:</h5>
        <p class="card-text">
          <strong>Language:</strong> {{ selectedLanguageLabel }}<br />
          <strong>Proficiency Level:</strong> {{ selectedProficiency }}
        </p>
        <a href="#" class="btn btn-primary" @click="onEditbutton">Edit</a>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, onBeforeMount, onMounted, ref } from "vue";
import { useUsersStore } from "@/stores";
import { useAuthStore } from "@/stores";
const languages = ref([]);
const proficiencyLevels = ["Beginner", "Intermediate", "Advanced"];
const showEditMode = ref(false);
const selectedLanguage = ref("");
const selectedProficiency = ref("");
const userStore = useUsersStore();
const authStore = useAuthStore();
const selectedLanguageLabel = ref("");
const showSaveButton = computed(() => {
  debugger;
  if (
    selectedLanguage.value == userStore.userLangData?.language_id &&
    selectedProficiency.value == userStore.userLangData?.profiency
  ) {
    return false;
  }
  return true;
});
const onCancelButton = () => {
  showEditMode.value = false;
};
const onLanguageChange = () => {
  debugger;
  selectedLanguageLabel.value = languages.value.find(
    (lang) => lang.id == selectedLanguage.value
  ).name;
  selectedProficiency.value = "";
};
const onEditbutton = () => {
  selectedLanguage.value = userStore.userLangData.language_id;
  selectedLanguageLabel.value = userStore.userLangData.language;
  selectedProficiency.value = userStore.userLangData.profiency;
  showEditMode.value = true;
};
const onSavePreference = async () => {
  debugger;
  let data = {
    language_id: selectedLanguage.value,
    profiency: selectedProficiency.value,
    user_id: authStore.uid,
  };
  await userStore.saveUserLangData(data);
  showEditMode.value = false;
};
onBeforeMount(async () => {
  languages.value = await userStore.getAllLanguages();
  console.log(languages.value);
  let langData = await userStore.getUserLangData();
  if (langData) {
    debugger;
    showEditMode.value = false;
    selectedLanguage.value = langData.language_id;
    selectedLanguageLabel.value = langData.language;
    selectedProficiency.value = langData.profiency;
  } else {
    selectedLanguage.value = "";
    selectedProficiency.value = "";
    showEditMode.value = true;
  }
});
</script>

<style scoped>
.card {
  border-radius: 10px; /* Rounded corners */
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); /* Subtle shadow */
}

.card-title {
  font-weight: bold;
}

.card-text {
  line-height: 1.6; /* Adjust line height for better readability */
}
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

.background-image {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: -1;
}

.bg-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  filter: blur(5px); /* Optional: Add a subtle blur effect */
}

.overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5); /* Semi-transparent overlay */
}

.message-title {
  font-size: 24px;
  font-weight: bold;
  color: #333; /* Adjust font color as needed */
}

.message-text {
  font-size: 18px;
  color: #666; /* Adjust font color as needed */
  line-height: 1.5;
}
</style>
