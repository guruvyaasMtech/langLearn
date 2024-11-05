<template>
  <div class="card m-3">
    <Alert />

    <h4 class="card-header">Change Password</h4>
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
            :value="email"
            :disabled="true"
            :class="{ 'is-invalid': errors.email }"
          />
          <div class="invalid-feedback">{{ errors.email }}</div>
        </div>
        <div class="form-group">
          <label>Current Password</label>
          <Field
            name="currentPassword"
            type="password"
            class="form-control"
            :class="{ 'is-invalid': errors.currentPassword }"
          />
          <div class="invalid-feedback">{{ errors.currentPassword }}</div>
        </div>
        <div class="form-group">
          <label>New Password</label>
          <Field
            name="newPassword"
            type="password"
            class="form-control"
            :class="{ 'is-invalid': errors.newPassword }"
          />
          <div class="invalid-feedback">{{ errors.newPassword }}</div>
        </div>
        <div class="form-group">
          <label>Confirm New Password</label>
          <Field
            name="confirmPassword"
            type="password"
            class="form-control"
            :class="{ 'is-invalid': errors.confirmPassword }"
          />
          <div class="invalid-feedback">{{ errors.confirmPassword }}</div>
        </div>
        <div class="form-group">
          <button class="btn btn-primary" :disabled="isSubmitting">
            <span
              v-show="isSubmitting"
              class="spinner-border spinner-border-sm mr-1"
            ></span>
            Change Password
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
import { computed, onMounted } from "vue";
import { Alert } from "@/components";
const alertStore = useAlertStore();
const authStore = useAuthStore();
const email = computed(() => authStore.tempEmail);
const schema = Yup.object().shape({
  email: Yup.string()
    .email("Invalid email format")
    .required("Email is required"),
  currentPassword: Yup.string().required("Current password is required"),
  newPassword: Yup.string()
    .required("New password is required")
    .min(12, "New password must be at least 12 characters")
    .matches(
      /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]+$/,
      "Password must contain at least one special character"
    ),
  confirmPassword: Yup.string()
    .required("Confirm password is required")
    .oneOf([Yup.ref("newPassword")], "Passwords must match"),
});

async function onSubmit(values) {
  alertStore.clear();
  const { email, currentPassword, newPassword } = values;
  try {
    if ((await authStore.login(email, currentPassword)) == "change-password") {
      debugger;
      const response = await authStore.changePassword(email, newPassword);
      debugger;
      if (response.data) {
        debugger;
        alertStore.success("Password changed successfully");

        emit("loginSuccess", { username: true });
      } else alertStore.error("Password change failed");
    } else {
      alertStore.error("Invalid current password");
    }
  } catch (error) {
    alertStore.error(error.message);
  }
}
</script>
