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
                            <div class="d-flex">
                                <input class="form-control mb-3 mb-lg-2 mb-sm-2 mb-xxl-2" type="text"
                                       placeholder="Search" v-model="params.keyword"/>
                                <button class="btn btn-sm btn-primary mb-3 mb-lg-2 mb-sm-2 mb-xxl-2" @click="getProductPage">
                                    <i class="fa fa-search"></i>
                                </button>
                            </div>
                            <!-- Categories -->
                            <div>
                                <h4>Category</h4>
                                <ul class="m-4">
                                    <template v-for="item in categoryList" :key="item.id">
                                        <div>
                                            <input :id="item.name" class="m-1" type="checkbox" :value="item.id"
                                                   :name="item.name"
                                                   v-model="params.categoryIdList"/>
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
                                                   v-model="params.brandIdList"/>
                                            <label :for="item.name">{{ item.name }}</label>
                                        </div>
                                    </template>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-10 box">
                        <div class="filters">
                            <ul>
                                <li class="active">Tất cả sản phẩm</li>
                            </ul>
                        </div>
                        <div>
                            <div class="filters-content">
                                <div class="row grid" v-if="productPage && productPage.totalElements > 0">
                                    <product-component
                                        v-for="product in productPage.content"
                                        :key="product.id"
                                        :class="PRODUCT_RESPONSIVE_CLASS.four"
                                        :product="product"
                                    />
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <page-component :last="productPage.last" :first="productPage.first" :key="productPage"
                                            :page="params?.page" :total-page="productPage.totalPages"
                                            :page-change="pageChange"/>
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
import {defineComponent, inject} from "vue";
import {PRODUCT_RESPONSIVE_CLASS} from "@/plugins/utils";
import ProductComponent from "@/components/product/ProductComponent.vue";
import LoadingComponent from "@/components/loading/LoadingComponent.vue";
import {ProductService} from "@/base/service/product.service";
import {BrandService} from "@/base/service/brand.service";
import {CategoryService} from "@/base/service/category.service";
import PageComponent from "@/components/pages/PageComponent.vue";
import {Category} from "@/base/model/category.model";
import {Brand} from "@/base/model/brand.model";

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
            categoryList: [] as Category[],
            brandList: [] as Brand[],
            productPage: {
                content: [],
                totalPages: 0,
                totalElements: 0,
                last: true,
                first: true,
                size: 0,
            } as any,
            params: {
                page: 1,
                size: 20,
                keyword: '',
                categoryIdList: [] as string[],
                brandIdList: [] as string[],
            },
        }
    },
    methods: {
        async getProductPage() {
            this.productPage = await this.productService.findByFilter(this.params);
        },
        async pageChange(page: number) {
            this.params.page = page;
            await this.getProductPage();
        },
        async loadCategoryList() {
            this.categoryList = await this.categoryService.findAllCategory();
        },
        async loadBrandList() {
            this.brandList = await this.brandService.findAllBrand();
        }
    },
    created() {
        this.getProductPage();
        this.loadCategoryList();
        this.loadBrandList();
    },
    setup() {
        return {
            PRODUCT_RESPONSIVE_CLASS,
            productService: inject('productService') as ProductService,
            brandService: inject('brandService') as BrandService,
            categoryService: inject('categoryService') as CategoryService,
        }
    }
})
</script>

<style scoped lang="css">
@import "style.css";
</style>