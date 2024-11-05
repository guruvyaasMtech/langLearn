<template>
  <div class="container">
    <div class="row">
      <div class="col-md-12">
        <h1 class="text-center">Welcome back</h1>
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
      <div class="col-md-4" v-for="(card, index) in cards" :key="index">
        <div class="card" @click="toggleLevels(card)">
          <div class="card-body text-center">
            <h5 class="card-title">{{ card.title }}</h5>
            <p class="card-text">{{ card.description }}</p>
          </div>
        </div>

        <!-- Level Cards -->
        <div v-if="card.showLevels" class="mt-3">
          <div
            class="card"
            v-for="level in levels"
            :key="level"
            @click="onLevelClick(level, card.title)"
          >
            <div class="card-body text-center">
              <h6 class="card-title">{{ level }}</h6>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import { useUsersStore } from "@/stores";

const userStore = useUsersStore();
const languages = ["English", "Spanish", "French", "German"];
const levels = ["Beginner", "Intermediate", "Advanced"];
const selectedLanguage = ref("");
const cards = ref([
  {
    title: "Vocabulary",
    description: "Learn vocabulary words.",
    showLevels: false,
  },
  {
    title: "Grammar",
    description: "Understand grammar rules.",
    showLevels: false,
  },
  {
    title: "Exercises",
    description: "Practice with exercises.",
    showLevels: false,
  },
]);

const onLanguageChange = () => {
  // Resetting the cards when the language is changed
  cards.value.forEach((card) => (card.showLevels = false));
};

const toggleLevels = (card) => {
  card.showLevels = !card.showLevels; // Toggle the display of levels
};

const onLevelClick = (level, cardTitle) => {
  alert(`You selected ${level} level for ${cardTitle}`);
};

onMounted(() => {
  let langData = userStore.getUserLangData();
  if (langData) {
    selectedLanguage.value = langData.language;
  } else {
    selectedLanguage.value = "";
  }
});
</script>

<style scoped>
.card {
  cursor: pointer;
  transition: transform 0.2s;
}

.card:hover {
  transform: scale(1.05);
}

.card-body {
  background-color: #f8f9fa;
}
</style>
