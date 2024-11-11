<script setup>
import { Nav, Alert } from "@/components";
import { useAuthStore } from "@/stores";
import { ref } from "vue";
import selectlanguage from "./views/account/selectlanguage.vue";
const authStore = useAuthStore();
const showSelectLanguage = ref(false);
const showLanguageChange = () => {
  showSelectLanguage.value = !showSelectLanguage.value;
};
</script>

<template>
  <div class="app-container" :class="authStore.user">
    <Nav @changeLanguage="showLanguageChange" />
    <div class="pt-4 pb-4" v-if="!showSelectLanguage">
      <router-view />
    </div>
    <div v-else>
      <selectlanguage />
    </div>
  </div>
</template>

<style>
@import "@/assets/base.css";
</style>
