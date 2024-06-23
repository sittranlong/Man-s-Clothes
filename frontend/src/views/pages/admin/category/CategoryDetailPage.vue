<script lang="ts">
import {defineComponent} from "vue";
import AdminPage from "@/views/pages/admin/AdminPage.vue";
import CategoryDetailComponent from "@/components/admin/category/CategoryDetailComponent.vue";

export default defineComponent({
    name: 'UserDetailPage',
    components: {CategoryDetailComponent, AdminPage}
})
</script>
<template>
    <admin-page>
        <category-detail-component/>
    </admin-page>
</template>