<template>
    <div>
        <loading-component :loading="true"/>
        <header-component/>
        <product-detail-component/>
    </div>
</template>

<script lang="ts">
import HeaderComponent from "@/components/header/HeaderComponent.vue";
import LoadingComponent from "@/components/loading/LoadingComponent.vue";
import ProductDetailComponent from "@/components/product/ProductDetailComponent.vue";
import { defineComponent } from "vue";

export default defineComponent({
    name: 'ProductDetailPage',
    components: {
        HeaderComponent,
        LoadingComponent,
        ProductDetailComponent
    }
});
</script>