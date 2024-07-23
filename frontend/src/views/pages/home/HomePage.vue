<script lang="tsx">
import {defineComponent, inject, ref} from "vue";
import HeaderComponent from '@/components/header/HeaderComponent.vue';
import FooterComponent from '@/components/footer/FooterComponent.vue';
import ProductComponent from '@/components/product/ProductComponent.vue';
import BannerComponent from '@/components/banner/BannerComponent.vue';
import {PRODUCT_RESPONSIVE_CLASS} from "@/plugins/utils";
import AboutComponent from "@/components/about/AboutComponent.vue";
import BuyNowComponent from "@/components/buynow/BuyNowComponent.vue";
import LoadingComponent from "@/components/loading/LoadingComponent.vue";
import {ProductService} from "@/base/service/product.service";
import type {ProductCard} from "@/base/model/product.model";

export default defineComponent({
    name: 'HomePage',
    components: {
        HeaderComponent,
        FooterComponent,
        ProductComponent,
        BannerComponent,
        AboutComponent,
        BuyNowComponent,
        LoadingComponent
    },
    setup() {
        const productList = ref([] as ProductCard[]);
        const productService = inject('productService') as ProductService;
        const loadProductList = async () => {
            productList.value = await productService.findByFilter({page: 1, size: 20});
        }
        const loadingComponent = <LoadingComponent loading={true}/>

        return () => (
            <div class="container-fluid">
                {loadingComponent}
                <header-component/>
                <banner-component/>
                <div class="container ">
                    <div class="col-md-12 mt-3">
                        <div class="section-heading">
                            <h2>Latest Products</h2>
                            <router-link to="/products">view all products <i class="fa fa-angle-right"></i>
                            </router-link>
                        </div>
                    </div>
                    <div class="row align-items-center">
                        {
                            productList.value.map((product, index) => (
                                <product-component class={PRODUCT_RESPONSIVE_CLASS.four} product={product} key={index}/>
                            ))
                        }
                    </div>
                </div>
                <about-component/>
                <buy-now-component/>
                <footer-component/>
            </div>
        )
    }
})
</script>

<style lang="css" scoped>
@import "style.css";
</style>