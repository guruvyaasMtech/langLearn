<script setup>
import { Form, Field } from "vee-validate";
import * as Yup from "yup";
import { defineEmits } from "vue";
import { useAuthStore } from "@/stores";
import { Alert } from "@/components";
import { onMounted } from "vue";
import { useAlertStore } from "@/stores";
const emit = defineEmits(["loginSuccess"]);
const alertStore = useAlertStore();
const schema = Yup.object().shape({
  username: Yup.string().required("Username is required"),
  password: Yup.string().required("Password is required"),
});

async function onSubmit(values) {
  const authStore = useAuthStore();
  const { username, password } = values;
  let temp = await authStore.login(username, password);
  if (temp == "success") {
    console.log("Login Successful");
    emit("loginSuccess", { username: true });
  } else if (temp == "change-password") {
    emit("changePassword", { change: true });
  }
}
async function resetPassword() {
  emit("forgotPassword");
}
async function closeLogin() {
  emit("loginSuccess", { username: false });
}
onMounted(() => {
  alertStore.clear();
});
</script>

<template>
  <div class="card m-3">
    <Alert />

    <h4 class="card-header">Login</h4>
    <div class="card-body">
      <Form
        @submit="onSubmit"
        :validation-schema="schema"
        v-slot="{ errors, isSubmitting }"
      >
        <div class="form-group">
          <label>Username</label>
          <Field
            name="username"
            type="text"
            class="form-control"
            :class="{ 'is-invalid': errors.username }"
          />
          <div class="invalid-feedback">{{ errors.username }}</div>
        </div>
        <div class="form-group">
          <label>Password</label>
          <Field
            name="password"
            type="password"
            class="form-control"
            :class="{ 'is-invalid': errors.password }"
          />
          <div class="invalid-feedback">{{ errors.password }}</div>
        </div>
        <div class="form-group">
          <button class="btn btn-primary" :disabled="isSubmitting">
            <span
              v-show="isSubmitting"
              class="spinner-border spinner-border-sm mr-1"
            ></span>
            Login
          </button>
          <button class="btn -btn-secondary" @click="closeLogin">cancel</button>
        </div>
        <a @click="resetPassword">Forgot Password?</a>
      </Form>
    </div>
  </div>
</template>
