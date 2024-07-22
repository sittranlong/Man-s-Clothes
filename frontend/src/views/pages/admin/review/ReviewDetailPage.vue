<script lang="ts">
import {defineComponent} from "vue";
import AdminPage from "@/views/pages/admin/AdminPage.vue";
import ReviewDetailComponent from "@/components/admin/review/ReviewDetailComponent.vue";

export default defineComponent({
    name: 'UserDetailPage',
    components: {ReviewDetailComponent, AdminPage}
})
</script>
<template>
    <admin-page>
        <review-detail-component/>
    </admin-page>
</template>