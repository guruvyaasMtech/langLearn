<template>
  <div class="mainClass">
    <div class="lesson-list" v-if="filteredMaterials.length">
      <ul>
        <li
          v-for="(material, index) in filteredMaterials"
          :key="material.id"
          @click="playVideo(material)"
          class="lesson-item"
          :class="{ active: selectedMaterial?.id === material.id }"
        >
          <h3>Lesson {{ index + 1 }}</h3>
        </li>
      </ul>
    </div>
    <div v-else class="no-lessons">No vocabulary lessons found.</div>

    <div class="lesson-container">
      <iframe
        v-if="selectedLessonUrl"
        :width="560"
        :height="315"
        :src="selectedLessonUrl || filteredMaterials[0].content_url"
        title="YouTube video player"
        frameborder="0"
        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
        allowfullscreen
      ></iframe>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from "vue";
import { useUsersStore } from "../../stores/users.store";

const userStore = useUsersStore();

const filteredMaterials = computed(() => {
  return userStore.materials.filter(
    (material) => material.category === "Grammar"
  );
});

const selectedLessonUrl = ref(filteredMaterials[0]?.content_url);
const selectedMaterial = ref(null);

const playVideo = (material) => {
  selectedLessonUrl.value = material?.content_url;
  selectedMaterial.value = material;
};
onMounted(() => {
  if (filteredMaterials.length) playVideo(filteredMaterials[0]);
  else selectedLessonUrl.value = null;
});
</script>

<style scoped>
.mainClass {
  font-family: Arial, sans-serif;
  margin: 2rem;
  padding: 1rem;
  border: 1px solid #ddd;
  border-radius: 5px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  background-color: #f5f5f5;
  display: flex; /* Arrange lessons and video side-by-side */
}

.lesson-list {
  width: 20%; /* Allocate space for lesson list */
  padding: 0;
  margin: 0;
  max-height: 300px; /* Set a maximum height to prevent overflow */
  overflow-y: auto; /* Enable scrolling if lessons exceed the height */
}

.lesson-list ul {
  list-style: none; /* Remove default bullet points */
  padding: 0;
  margin: 0;
}
.lesson-item {
  display: flex; /* Arrange lesson title and description side-by-side */
  align-items: center; /* Align content vertically */
  margin-bottom: 1rem;
  padding: 1rem;
  border-radius: 5px;
  cursor: pointer; /* Indicate clickable lessons */
  background-color: #fff; /* Light background */
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); /* Subtle shadow */
  transition: all 0.2s ease-in-out; /* Smooth transitions on hover */
  color: #333;
  border-left: 5px solid transparent; /* Optional border for visual separation */
}

.lesson-item:hover {
  background-color: #ececec; /* Lighten background on hover */
  box-shadow: 0 3px 7px rgba(0, 0, 0, 0.15); /* Increase shadow on hover */
  border-left-color: #e0f2f1; /* Change border color on hover (optional) */
}

.lesson-item.active {
  background-color: #e0f2f1; /* Change background for active lesson */
  box-shadow: 0 4px 8px rgba(150, 219, 181, 0.2); /* Greenish shadow for active */
  font-weight: bold; /* Make active lesson text bolder */
}

.lesson-title {
  margin-bottom: 0.5rem;
  font-size: 1.2rem;
  color: #333;
}

.lesson-container {
  width: 80%;
  padding: 1rem;
}

.lesson-iframe {
  border: none;
  width: 100%; /* Fill the entire container */
  min-height: 300px;
  border-radius: 5px;
  box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.1);
}
</style>
