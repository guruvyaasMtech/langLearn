import { createRouter, createWebHistory } from "vue-router";

import { useAuthStore, useAlertStore } from "@/stores";
import { Home } from "@/views";
import accountRoutes from "./account.routes";
import usersRoutes from "./users.routes";
import Selectlanguage from "../views/account/selectlanguage.vue";

export const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  linkActiveClass: "active",
  routes: [
    { path: "/", component: Home },
    { path: "/home", component: Home },
    { path: "/selectLanguage", component: Selectlanguage },
    { ...accountRoutes },
    { ...usersRoutes },
    // catch all redirect to home page
    { path: "/:pathMatch(.*)*", redirect: "/" },
  ],
});

router.beforeEach(async (to) => {
  // clear alert on route change
  const alertStore = useAlertStore();
  alertStore.clear();
});
