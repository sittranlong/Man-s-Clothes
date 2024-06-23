<script lang="ts">
import {defineComponent} from "vue";
import AdminPage from "@/views/pages/admin/AdminPage.vue";
import CategoryComponent from "@/components/admin/category/CategoryComponent.vue";

export default defineComponent({
    name: 'CategoryPage',
    components: {CategoryComponent, AdminPage}
})
</script>
<template>
    <admin-page>
        <category-component/>
    </admin-page>
</template>