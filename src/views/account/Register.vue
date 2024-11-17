<script setup>
import { Form, Field } from "vee-validate";
import * as Yup from "yup";
import { Alert } from "@/components";

import { useUsersStore, useAlertStore } from "@/stores";
import { router } from "@/router";
import { onMounted } from "vue";
import { useAuthStore } from "../../stores/auth.store";
const authStore = useAuthStore();
const alertStore = useAlertStore();
const schema = Yup.object().shape({
  firstName: Yup.string().required("First Name is required"),
  lastName: Yup.string().required("Last Name is required"),
  username: Yup.string()
    .matches(
      /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
      "Invalid email format"
    )
    .required("Email is required"),
  password: Yup.string()
    .required("Password is required")
    .min(12, "Password must be at least 12 characters")
    .matches(
      /^(?=.*[!@#$%^&*])/,
      "Password must contain at least one special character"
    ),
});
let disableSubmit = false;
const emit = defineEmits(["loginSuccess"]);
async function onSubmit(values) {
  alertStore.clear();
  const user = JSON.stringify({
    full_name: values.firstName + values.lastName,
    mobile: "9123456789",
    email: values.username,
    password: values.password,
  });
  const usersStore = useUsersStore();

  try {
    const temp = await authStore.register(user);
    if (temp.data) {
      disableSubmit = true;
      alertStore.success("Registration successful.Please login again");
      router.push("/user-home");
      await new Promise((resolve) => setTimeout(resolve, 20000));
      //emit("loginSuccess", { username: true });
    } else {
      alertStore.error("Registration failed.User Already Exists");
    }
  } catch (error) {
    alertStore.error(error);
  }
}
onMounted(() => {
  alertStore.clear();
  disableSubmit = false;
});
</script>

<template>
  <div class="card m-3">
    <Alert />

    <h4 class="card-header">Register</h4>
    <div class="card-body">
      <Form
        @submit="onSubmit"
        :validation-schema="schema"
        v-slot="{ errors, isSubmitting }"
      >
        <div class="form-group">
          <label>First Name</label>
          <Field
            name="firstName"
            type="text"
            class="form-control"
            :class="{ 'is-invalid': errors.firstName }"
          />
          <div class="invalid-feedback">{{ errors.firstName }}</div>
        </div>
        <div class="form-group">
          <label>Last Name</label>
          <Field
            name="lastName"
            type="text"
            class="form-control"
            :class="{ 'is-invalid': errors.lastName }"
          />
          <div class="invalid-feedback">{{ errors.lastName }}</div>
        </div>
        <div class="form-group">
          <label>Email(Username)</label>
          <Field
            name="username"
            type="Email"
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
          <button
            class="btn btn-primary"
            :disabled="isSubmitting || disableSubmit"
          >
            <span
              v-show="isSubmitting"
              class="spinner-border spinner-border-sm mr-1"
            ></span>
            Register
          </button>
          <router-link to="login" class="btn btn-link">Cancel</router-link>
        </div>
      </Form>
    </div>
  </div>
</template>
