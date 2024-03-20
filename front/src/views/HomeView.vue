<script setup lang="ts">
import axios from "axios";
import { ref } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

const posts = ref([]);

axios.get("/api/posts?page=1&size=10").then((response) => {
  response.data.forEach((post: any) => {
    posts.value.push(post);
  });
});
</script>

<template>
  <ul>
    <li v-for="post in posts" :key="post.id">
      <div class="title">
        <router-link :to="{ name: 'read', params: { postId: post.id } }">{{ post.title }}</router-link>
      </div>

      <div class="content">
        {{ post.content }}
      </div>

      <div class="sub d-flex">
        <div class="category">개발</div>
        <div class="regDate">2022-06-01</div>
      </div>
    </li>
  </ul>
</template>

<style scoped lang="scss">
ul {
  list-style: none;
  padding: 0;
  li {
    margin-bottom: 1.3rem;

    .title {
      a {
        font-size: 1.2rem;
        color: lightcoral;
        text-decoration: none;
      }

      &:hover {
        text-decoration: underline;
        color: #ff7217
      }


    }
    .content {
      font-size: 0.9rem;
      color: darkblue;
    }
    &:last-child {
      margin-bottom: 0;
    }
    .sub {
      margin-top: 4px;
      font-size: 0.78rem;

      .regDate {
        margin-left: 10px;
        color: cornflowerblue;
        margin-left: 10px;
      }
    }
  }
}
</style>
