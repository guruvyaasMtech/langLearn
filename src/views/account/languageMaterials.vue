<template>
  <div class="language-materials">
    <!--    <div class="card">
      <div class="card-body">
        <h5 class="card-title">You have selected:</h5>
        <p class="card-text">
          <strong>Language:</strong> {{ selectedLanguageLabel }}<br />
          <strong>Proficiency Level:</strong> {{ selectedProficiency }}
        </p>
      </div>
    </div> -->

    <ul class="nav nav-tabs">
      <li class="nav-item">
        <a
          class="nav-link"
          :class="{ active: currentTab === 'grammar' }"
          href="#"
          @click="showTab('grammar')"
          >Grammar</a
        >
      </li>
      <li class="nav-item">
        <a
          class="nav-link"
          :class="{ active: currentTab === 'vocabulary' }"
          href="#"
          @click="showTab('vocabulary')"
          >Vocabulary</a
        >
      </li>
    </ul>
    <div>
      <div v-if="currentTab === 'grammar'" class="tab-pane active">
        <Grammar />
      </div>
      <div v-else class="tab-pane">
        <Vocabulary />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeMount } from "vue";
import { useUsersStore } from "@/stores";
import Grammar from "./grammar.vue";
import Vocabulary from "./vocablary.vue";

const currentTab = ref("grammar");
const selectedLanguage = ref(null);
const selectedLanguageLabel = ref(null);
const selectedProficiency = ref(null);
const showEditMode = ref(false);

const userStore = useUsersStore();

const showTab = (tab) => {
  currentTab.value = tab;
};

onBeforeMount(async () => {
  const langData = await userStore.getUserLangData();
  selectedProficiency.value = langData.profiency;

  const Materials = await userStore.getVocablaryData(selectedProficiency.value);

  showEditMode.value = false;
  selectedLanguage.value = langData.language_id;
  selectedLanguageLabel.value = langData.language;
});
</script>

<style scoped>
.language-materials {
  background-color: #fff;
  padding: 20px;
  border-radius: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  color: black;
}

.nav-tabs {
  border-bottom: 1px solid #ddd;
}

.nav-link {
  border-radius: 0;
  border: none;
  border-bottom: 1px solid transparent;
}

.nav-link.active {
  border-bottom: 2px solid #007bff;
  color: #007bff;
}

.tab-content {
  padding-top: 20px;
}

.card {
  border-radius: 10px; /* Rounded corners */
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); /* Subtle shadow */
}

.card-title {
  font-weight: bold;
  color: black;
}

.card-text {
  line-height: 1.6; /* Adjust line height for better readability */
  color: black;
}
</style>
