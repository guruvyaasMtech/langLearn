import { defineStore } from "pinia";
import axios from "axios";
import { router } from "@/router";
import { useAlertStore } from "@/stores";

const baseUrl = `${import.meta.env.VITE_API_URL}/api/users`;

export const useAuthStore = defineStore({
  id: "auth",
  state: () => ({
    user: JSON.parse(localStorage.getItem("user")),
    returnUrl: "/user-home",
    tempEmail: "",
    uid: "",
  }),
  actions: {
    async login(username, password) {
      const alertStore = useAlertStore();
      alertStore.clear();

      try {
        const requestBody = {
          email: username,
          password: password,
        };

        const response = await axios.post(
          `${baseUrl}/validatePassword`,
          requestBody,
          {
            headers: {
              "Content-Type": "application/json",
            },
          }
        );

        const user = response.data;
        this.tempEmail = user.email;
        this.uid = user.id;
        if (user.tempPassword) {
          return "change-password";
        }

        if (!user) {
          throw new Error("Invalid Username or Password");
        }

        alertStore.success("Login successful");
        this.user = user;
        localStorage.setItem("user", JSON.stringify(user));

        return "success";
      } catch (error) {
        alertStore.error(error.message || "An error occurred during login");
        return "error";
      }
    },
    async sendPasswordResetLink(email) {
      const json = JSON.stringify({ email: email });
      try {
        const response = await axios.post(`${baseUrl}/resetPassword`, json, {
          headers: {
            "Content-Type": "application/json",
          },
        });
        return response;
      } catch (error) {}
    },

    async changePassword(email, password) {
      const json = JSON.stringify({
        email: email,
        password: password,
        id: this.uid,
      });
      const response = await axios.post(`${baseUrl}/updatePassword`, json, {
        headers: {
          "Content-Type": "application/json",
        },
      });
      return response;
    },
    logout() {
      const alertStore = useAlertStore();

      alertStore.clear();

      this.user = null;
      localStorage.removeItem("user");
      router.push("/account/home");
    },
  },
});
