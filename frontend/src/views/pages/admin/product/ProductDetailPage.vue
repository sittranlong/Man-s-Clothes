<script lang="ts">
import {defineComponent} from "vue";
import AdminPage from "@/views/pages/admin/AdminPage.vue";
import ProductDetailComponent from "@/components/admin/product/ProductDetailComponent.vue"

export default defineComponent({
    name: 'ProductDetailPage',
    components: {ProductDetailComponent, AdminPage}
})
</script>
<template>
    <admin-page>
        <product-detail-component/>
    </admin-page>
</template>