<script lang="ts">
import {defineComponent, inject} from 'vue'
import {toast} from "vue3-toastify";
import {ProductService} from "@/base/service/product.service";
import {useRouter} from "vue-router";
import formatMoney from "@/plugins/utils";

export default defineComponent({
    name: 'ProductComponent',
    setup() {
        const headers = [
            {title: 'STT', value: 'index', sortable: false, align: 'center'},
            {title: 'Ảnh', value: 'image', sortable: false},
            {title: 'Tên sản phẩm', value: 'name'},
            {title: 'Giá', value: 'price'},
            {title: 'Thương hiệu', value: 'brand.name', align: 'center'},
            {title: 'Danh mục', value: 'category.name', align: 'center'},
            {title: 'Hành động', value: 'action'},

        ];
        return {
            headers,
            productService: inject('productService') as ProductService,
            router: useRouter()
        }
    },
    data() {
        return {
            productPage: {
                content: [],
                totalPages: 0,
                totalElements: 0,
                last: true,
                size: 0,
            } as any,
            searchParams: {
                page: 0,
                size: 10,
                keyword: '',
            },
            isLoading: false,
        }
    },
    methods: {
        async search() {
            this.isLoading = true;
            this.productService.findBySearch(this.searchParams).then((data) => {
                this.productPage = data;
            }).finally(() => {
                this.isLoading = false;
            });
        },
        viewDetail(item: any) {
            this.router.push('/admin/product/' + item.id);
        },
        deleteProduct(item: any) {
            if (item) {
                if (confirm('Bạn có chắc chắn muốn xóa sản phẩm này ?')) {
                    this.productService.delete(item?.id).then(() => {
                        this.search();
                        toast.success('Xóa sản phẩm thành công');
                    });
                }
            }
        },
        calcPriceMarket(product : any) {
            return formatMoney(product.price - (product.price * product.discountPercent / 100));
        },
        formatNumber(number: number) {
            return formatMoney(number);
        }
    },
    created() {
        this.search();
    }

})
</script>
<template>
    <div class="text-dark">
        <h1>Danh sách sản phẩm</h1>
        <v-text-field
            v-model="searchParams.keyword"
            label="Search"
            prepend-inner-icon="mdi-magnify"
            variant="outlined"
            hide-details
            @change="search"
            single-line
        ></v-text-field>
        <v-btn color="primary" @click="() => router.push('/admin/product/new')">Thêm mới</v-btn>
        <v-data-table
            :headers="headers"
            :items="productPage.content"
            :loading="isLoading"
            :items-per-page="searchParams.size"
            :on-update:items-per-page="search"
            :page="searchParams.page"
            :on-update:page="search"
            :search="searchParams.keyword"
            :total-items="productPage.totalElements">
            <template v-slot:[`item.index`]="{index}">
                <td>{{ index + 1 }}</td>
            </template>
<!--            gía bán, giá giảm-->
            <template v-slot:[`item.price`]="{item}">
                <td>
                    <ul>
                        <li>{{ formatNumber(item.price) }}</li>
                        <li v-if="item?.discountPercent > 0"> <strike>{{ calcPriceMarket(item) }}</strike></li>
                    </ul>
                </td>
            </template>
            <template v-slot:[`item.image`]="{item}">
                <v-img :src="'data:image/png;base64,'+ item?.image"></v-img>
            </template>
            <template v-slot:[`item.action`]="{item}">
                <td class="align-center">
                    <v-icon @click="viewDetail(item)">mdi-eye</v-icon>
                    <v-icon @click="deleteProduct(item)">mdi-delete</v-icon>
                </td>
            </template>
        </v-data-table>
    </div>
</template>