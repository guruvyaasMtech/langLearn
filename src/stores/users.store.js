import { defineStore } from "pinia";

import { fetchWrapper } from "@/helpers";
import { useAuthStore } from "@/stores";
import axios from "axios";

const baseUrl = `${import.meta.env.VITE_API_URL}/api/language`;

export const useUsersStore = defineStore({
  id: "users",
  state: () => ({
    users: {},
    user: {},
    userLangData: null,
    materials: [],
  }),
  actions: {
    async getVocablaryData(prof) {
      const authStore = useAuthStore();

      const responnse = await axios.get(
        `${baseUrl}/getVocablaryData?user_id=${authStore.user.id}&profiency=${prof}`
      );
      this.materials = responnse.data;
      return responnse.data;
    },
    async register(user) {
      const response = await axios.post(`${baseUrl}/create`, user, {
        headers: {
          "Content-Type": "application/json",
        },
      });
      return response;
    },
    async getUserLangData() {
      const authStore = useAuthStore();
      try {
        const response = await axios.get(
          `${baseUrl}/getUserLangData?user_id=${authStore.user.id}`
        );
        this.userLangData = response.data;
        return response.data;
      } catch (error) {
        console.error("Error fetching language data:", error);
        return null;
      }
    },
    async getAllLanguages() {
      try {
        const response = await axios.get(`${baseUrl}`);
        return response.data;
      } catch (error) {
        console.error("Error fetching languages:", error);
        return null;
      }
    },
    async saveUserLangData(data) {
      try {
        const response = await axios.post(
          `${baseUrl}?user_id=${data.user_id}&language_id=${data.language_id}&profiency=${data.profiency}`,
          data
        );
        return response.data;
      } catch (error) {
        console.error("Error fetching languages:", error);
        return null;
      }
    },
    async getAll() {
      this.users = { loading: true };
      try {
        this.users = await fetchWrapper.get(baseUrl);
      } catch (error) {
        this.users = { error };
      }
    },
    async getById(id) {
      this.user = { loading: true };
      try {
        this.user = await fetchWrapper.get(`${baseUrl}/${id}`);
      } catch (error) {
        this.user = { error };
      }
    },
    async update(id, params) {
      await fetchWrapper.put(`${baseUrl}/${id}`, params);

      // update stored user if the logged in user updated their own record
      const authStore = useAuthStore();
      if (id === authStore.user.id) {
        // update local storage
        const user = { ...authStore.user, ...params };
        localStorage.setItem("user", JSON.stringify(user));

        // update auth user in pinia state
        authStore.user = user;
      }
    },
    async delete(id) {
      // add isDeleting prop to user being deleted
      this.users.find((x) => x.id === id).isDeleting = true;

      await fetchWrapper.delete(`${baseUrl}/${id}`);

      // remove user from list after deleted
      this.users = this.users.filter((x) => x.id !== id);

      // auto logout if the logged in user deleted their own record
      const authStore = useAuthStore();
      if (id === authStore.user.id) {
        authStore.logout();
      }
    },
  },
});
