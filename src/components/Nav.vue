<script setup>
import { useAuthStore } from "@/stores";
import { useRouter } from "vue-router";

const authStore = useAuthStore();
const route = useRouter();

function logout() {
  authStore.logout();
}
</script>

<template>
  <nav v-show="authStore.user" class="navbar navbar-expand navbar-dark bg-dark">
    <div class="navbar-nav">
      <button @click="logout" class="btn btn-link nav-item nav-link">
        Logout
      </button>
      <button
        @click="$emit('changeLanguage')"
        class="btn btn-link nav-item nav-link"
      >
        Change Language preference
      </button>
    </div>
  </nav>
</template>

<style scoped>
.language-popup {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #fff;
  border: 1px solid #ccc;
  padding: 20px;
  z-index: 999;
}
</style>
