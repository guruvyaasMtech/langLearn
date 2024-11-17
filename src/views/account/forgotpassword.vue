<template>
  <div class="card m-3">
    <Alert />
    <h4 class="card-header">Forgot Password</h4>
    <div class="card-body">
      <Form
        @submit="onSubmit"
        :validation-schema="schema"
        v-slot="{ errors, isSubmitting }"
      >
        <div class="form-group">
          <label>Email</label>
          <Field
            name="email"
            type="email"
            class="form-control"
            :class="{ 'is-invalid': errors.email }"
          />
          <div class="invalid-feedback">{{ errors.email }}</div>
        </div>
        <div class="form-group">
          <button
            class="btn btn-primary"
            :disabled="isSubmitting || isDisabled"
          >
            <span
              v-show="isSubmitting"
              class="spinner-border spinner-border-sm mr-1"
            ></span>
            Send Reset Link
          </button>
        </div>
      </Form>
    </div>
  </div>
</template>

<script setup>
import { Form, Field } from "vee-validate";
import * as Yup from "yup";
import { useAuthStore } from "@/stores";
import { useAlertStore } from "@/stores";
import Alert from "@/components/Alert.vue";
import { onMounted } from "vue";
const authStore = useAuthStore();
const alertStore = useAlertStore();
let isDisabled = false;
const schema = Yup.object().shape({
  email: Yup.string()
    .email("Invalid email format")
    .required("Email is required"),
});
onMounted(() => {
  isDisabled = false;
  alertStore.clear();
});
async function onSubmit(values) {
  isDisabled = true;
  const alertStore = useAlertStore();
  alertStore.clear();
  const { email } = values;
  try {
    const response = await authStore.sendPasswordResetLink(email);
    if (response.data == "success") {
      alertStore.success("Password reset link sent to your email");
    } else if (response.data == "No User") {
      isDisabled = false;

      alertStore.error("User not found.Please enter a valid email");
    }
  } catch (error) {
    isDisabled = false;
    alertStore.error("Some error occured. Please try again later");
  }
}
</script>
