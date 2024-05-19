<template>
    <div>
        <loading-component :loading="true"/>
        <header-component/>
        <div class="page-heading products-heading header-text">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <div class="text-content">
                            <h4>new arrivals</h4>
                            <h2>sixteen products</h2>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="products">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-2 box">
                        <div class="filters">
                            <ul>
                                <li class="active" data-filter="*">Filter</li>
                            </ul>
                        </div>
                        <div>
                            <div>
                                <input class="form-control mb-3 mb-lg-2 mb-sm-2 mb-xxl-2" type="text"
                                       placeholder="Search"/>
                            </div>
                            <!-- Categories -->
                            <div>
                                <h4>Category</h4>
                                <ul class="m-4">
                                    <template v-for="item in categoryList" :key="item.id">
                                        <div>
                                            <input :id="item.name" class="m-1" type="checkbox" :value="item.id"
                                                   :name="item.name"
                                                   v-model="categoryIdListSelected"/>
                                            <label :for="item.name">{{ item.name }}</label>
                                        </div>
                                    </template>
                                </ul>
                            </div>
                            <!-- Brands -->
                            <div>
                                <h4>Brand</h4>
                                <ul class="m-4">
                                    <template v-for="item in brandList" :key="item.id">
                                        <div>
                                            <input :id="item.name" class="m-1" type="checkbox" :value="item.id"
                                                   :name="item.name"
                                                   v-model="brandIdListSelected"/>
                                            <label :for="item.name">{{ item.name }}</label>
                                        </div>
                                    </template>
                                </ul>
                            </div>
                            <!-- Price -->
                            <div>
                                <h4>Price</h4>
                                <ul class="m-4">
                                    <template v-for="priceRange in priceRanges" :key="priceRange.value">
                                        <div>
                                            <input :id="priceRange.label" class="m-1" type="checkbox"
                                                   :value="priceRange.value"
                                                   :name="priceRange.label" v-model="priceRangeListSelected"/>
                                            <label :for="priceRange.label">{{ priceRange.label }}</label>
                                        </div>
                                    </template>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-10 box">
                        <div class="filters">
                            <ul>
                                <li class="active">All Products</li>
                                <li>Featured</li>
                                <li>Flash Deals</li>
                                <li>Last Minute</li>
                            </ul>
                        </div>
                        <div>
                            <div class="filters-content">
                                <div class="row grid" v-if="productList && productList.length > 0">
                                    <product-component
                                        v-for="product in productList"
                                        :key="product.title"
                                        :class="PRODUCT_RESPONSIVE_CLASS.four"
                                        :product="product"
                                    />
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <page-component :page="0" :total-page="10" @page-change.prevent="() => {}"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <footer-component/>
    </div>
</template>


<script lang="ts">
import HeaderComponent from "@/components/header/HeaderComponent.vue";
import FooterComponent from "@/components/footer/FooterComponent.vue";
import {defineComponent} from "vue";
import {ProductCard} from "@/base/model/product.model";
import {PRODUCT_RESPONSIVE_CLASS} from "@/plugins/utils";
import ProductComponent from "@/components/product/ProductComponent.vue";
import PageComponent from "@/components/pages/PageComponent.vue";
import LoadingComponent from "@/components/loading/LoadingComponent.vue";
import {ProductService} from "@/base/service/product.service";

export default defineComponent({
    name: 'ProductPage',
    components: {
        HeaderComponent,
        FooterComponent,
        ProductComponent,
        PageComponent,
        LoadingComponent
    },
    data() {
        return {
            brandIdListSelected: [],
            categoryIdListSelected: [],
            priceRangeListSelected: [],
            productList: [] as ProductCard[]
        }
    },
    methods: {
        async getProductList() {
            const productService = new ProductService();
            const res = await productService.findAll();
            this.productList = res;
        }
    },
    mounted() {
        this.getProductList();
    },
    setup() {
        const categoryList = [
            {id: 1, name: 'Category 1'},
            {id: 2, name: 'Category 2'},
            {id: 3, name: 'Category 3'},
            {id: 4, name: 'Category 4'},
            {id: 5, name: 'Category 5'},
        ];
        const brandList = [
            {id: 1, name: 'Brand 1'},
            {id: 2, name: 'Brand 2'},
            {id: 3, name: 'Brand 3'},
            {id: 4, name: 'Brand 4'},
            {id: 5, name: 'Brand 5'},
        ];
        const priceRanges = [
            {value: 1, label: 'Price 1'},
            {value: 2, label: 'Price 2'},
            {value: 3, label: 'Price 3'},
            {value: 4, label: 'Price 4'},
            {value: 5, label: 'Price 5'},
        ];
        return {
            categoryList,
            brandList,
            priceRanges,
            PRODUCT_RESPONSIVE_CLASS
        }
    }
})
</script>

<style scoped lang="css">
@import "style.css";
</style>