<script lang="ts">
import {defineComponent} from "vue";
import AdminPage from "@/views/pages/admin/AdminPage.vue";
import PostComponent from "@/components/admin/post/PostComponent.vue";

export default defineComponent({
    name: 'CategoryPage',
    components: {PostComponent, AdminPage}
})
</script>
<template>
    <admin-page>
        <post-component/>
    </admin-page>
</template>