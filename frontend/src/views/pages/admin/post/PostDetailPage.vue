<script lang="ts">
import {defineComponent} from "vue";
import AdminPage from "@/views/pages/admin/AdminPage.vue";
import PostDetailComponent from "@/components/admin/post/PostDetailComponent.vue";

export default defineComponent({
    name: 'UserDetailPage',
    components: {PostDetailComponent, AdminPage}
})
</script>
<template>
    <admin-page>
        <post-detail-component/>
    </admin-page>
</template>