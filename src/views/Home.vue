<script setup>
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { useAuthStore } from "@/stores";
import Register from "./account/Register.vue";
import ForgotPassword from "./account/ForgotPassword.vue";
import ChangePassword from "./account/ChangePassword.vue";
import { ref } from "vue";
import Login from "./account/Login.vue";
import selectlanguage from "./account/selectlanguage.vue";
const authStore = useAuthStore();
const { user } = storeToRefs(authStore);
const route = useRouter();
const currentComponent = ref(null);
const showProfileChanges = ref(false);
function navigateToLogin() {
  currentComponent.value = Login;
}

function navigateToRegister() {
  currentComponent.value = Register;
}
function navigateToForgotPassword() {
  currentComponent.value = ForgotPassword;
}
function navigateToChangePassword() {
  currentComponent.value = ChangePassword;
}
function navigateToLanguageChange() {
  currentComponent.value = selectlanguage;
}
function closeModal() {
  currentComponent.value = null;
}

function handleLoginSuccess(data) {
  closeModal();
}
</script>

<template>
  <div class="home-page">
    <div class="background-image">
      <img
        src="../assets/LangLearn.jpeg"
        alt="LangLearn Background"
        class="bg-image"
      />
      <div class="overlay"></div>
    </div>

    <div class="content col-md-9">
      <p v-if="!authStore.user" class="catchline">
        Your Gateway to Language Mastery.
      </p>
      <p v-if="!authStore.user" class="catchline">
        A Single Stop solution to make you language learning journey
      </p>
      <p v-if="authStore.user" class="catchline">
        Welcome, {{ authStore.user.full_name }}
      </p>
      <transition name="fade">
        <div class="button-container">
          <button
            v-if="!authStore.user"
            type="button"
            class="btn btn-warning"
            @click="navigateToLogin"
          >
            Continue Your Journey
          </button>
          &nbsp;
          <button
            v-if="!authStore.user"
            type="button"
            class="btn btn-warning"
            @click="navigateToRegister"
          >
            Begin Your Journey
          </button>
        </div>
      </transition>
    </div>
  </div>

  <transition name="slide">
    <div v-if="currentComponent" class="modal-overlay" @click="closeModal">
      <div class="modal-content" @click.stop>
        <button class="close-button" @click="closeModal">X</button>
        <component
          :is="currentComponent"
          @loginSuccess="handleLoginSuccess"
          @forgotPassword="navigateToForgotPassword"
          @changePassword="navigateToChangePassword"
        ></component>
      </div>
    </div>
  </transition>
</template>

<style scoped>
.home-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  position: relative;
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

.content {
  padding-top: 20%;
  color: #fff;
}

.catchline {
  font-size: 2em;
  font-weight: bold;
  color: #fff; /* White text for contrast */
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5); /* Subtle shadow effect */
  margin-bottom: 20px;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: flex-start;
  justify-content: flex-end;
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  position: relative;
  width: 30%;
  max-width: 400px;
  height: 100%;
  box-shadow: -2px 0 5px rgba(0, 0, 0, 0.1);
}

.close-button {
  position: absolute;
  top: 10px;
  right: 10px;
  background: none;
  border: none;
  font-size: 20px;
  cursor: pointer;
}

.slide-enter-active,
.slide-leave-active {
  transition: transform 0.3s ease;
}

.slide-enter,
.slide-leave-to {
  transform: translateX(100%);
}
</style>
