<script setup>
import { useAuthStore } from "@/stores";
import { ref } from "vue";

const authStore = useAuthStore();
const showModal = ref(false);

function toggleModal() {
  showModal.value = !showModal.value;
}
</script>

<template>
  <nav v-show="authStore.user" class="navbar navbar-expand navbar-dark bg-dark">
    <div class="navbar-nav">
      <router-link to="/" class="nav-item nav-link">Home</router-link>
      <button
        @click="authStore.logout()"
        class="btn btn-link nav-item nav-link"
      >
        Logout
      </button>
      <button
        type="button"
        class="btn btn-link nav-item nav-link"
        data-bs-toggle="modal"
        data-bs-target="#exampleModal"
        @click="toggleModal"
      >
        Launch demo modal
      </button>
    </div>
  </nav>

  <div
    class="modal fade"
    id="exampleModal"
    tabindex="-1"
    aria-labelledby="exampleModalLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="exampleModalLabel">Modal title</h1>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">...</div>
        <div class="modal-footer">
          <button
            type="button"
            class="btn btn-secondary"
            data-bs-dismiss="modal"
          >
            Close
          </button>
          <button type="button" class="btn btn-primary">Save changes</button>
        </div>
      </div>
    </div>
  </div>
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
