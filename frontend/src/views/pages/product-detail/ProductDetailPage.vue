<script lang="tsx">
import {defineComponent, ref} from "vue";
import {useRoute} from "vue-router";
import ProductDetailComponent from "@/components/product-detail/ProductDetailComponent.vue";
import {ProductService} from "@/base/service/product.service";

export default defineComponent({
    name: 'ProductDetailPage',
    components: {
        ProductDetailComponent
    },
    setup() {
        const route = useRoute();
        const productService = new ProductService();
        const productId = route.params.id;
        if (!productId) {
            console.error('Product ID is required');
        }
        // let product = productService.getById(productId);
        // if (!product) {
        //     console.error('Product not found');
        // }
        const product = ref(productService.getById(productId));
        return () => (
            <div class="container">
                <ProductDetailComponent product={product.value}/>
            </div>
        )
    }
})
</script>