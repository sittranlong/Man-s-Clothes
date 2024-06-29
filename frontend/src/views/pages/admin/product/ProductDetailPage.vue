<script lang="ts">
import {defineAsyncComponent, defineComponent} from "vue";
import AdminPage from "@/views/pages/admin/AdminPage.vue";

const ProductDetailComponent = defineAsyncComponent(() => import('@/components/admin/product/ProductDetailComponent.vue'));
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