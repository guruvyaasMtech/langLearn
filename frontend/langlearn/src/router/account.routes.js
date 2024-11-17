import { Layout, Login, Register } from "@/views/account";
import HomeUser from "../views/post-login/home-user.vue";
import Home from "../views/Home.vue";

export default {
  path: "/account",
  component: Layout,
  children: [
    { path: "", component: Home },
    { path: "user-home", component: HomeUser },
  ],
};
