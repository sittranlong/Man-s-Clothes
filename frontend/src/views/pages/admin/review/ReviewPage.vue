<script lang="ts">
import {defineComponent} from "vue";
import AdminPage from "@/views/pages/admin/AdminPage.vue";
import ReviewComponent from "@/components/admin/review/ReviewComponent.vue";

export default defineComponent({
    name: 'CategoryPage',
    components: {ReviewComponent, AdminPage}
})
</script>
<template>
    <admin-page>
        <review-component/>
    </admin-page>
</template>