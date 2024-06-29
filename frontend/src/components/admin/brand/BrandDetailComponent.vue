<script lang="ts">
import {defineComponent, inject} from "vue";
import {Brand} from "@/base/model/brand.model";
import {BrandService} from "@/base/service/brand.service";
import {toast} from "vue3-toastify";
import {useRoute, useRouter} from "vue-router";

export default defineComponent({
    name: 'BrandDetailComponent',
    setup() {
        return {
            brandService: inject('brandService') as BrandService,
            router : useRouter(),
            route : useRoute()
        }
    },
    data() {
        return {
            brand: new Brand(),
            id: '' as string,
            brandList: [] as Brand[]
        }
    },
    methods: {
        back() {
            this.router.push('/admin/brand');
        },
        loadBrandList() {
            this.brandService.findAll().then((data: Brand[]) => {
                this.brandList = data;
            });
        },
        submit() {
            if (this.brand.id) {
                this.brandService.update(this.brand).then(() => {
                    this.router.push('/admin/brand');
                    toast.success('Cập nhật thành công');
                });
            } else {
                this.brandService.save(this.brand).then(() => {
                    this.router.push('/admin/brand');
                    toast.success('Thêm mới thành công');
                });
            }
        }
    },
    created() {
        this.id = this.route?.params.id as string;
        this.brandService.get(this.id).then((data: Brand) => {
            this.brand = data;
        });
        this.loadBrandList();
    }
})
</script>
<template>
    <div class="text-dark text-center">
        <div>
            <h1>Chi tiết thương hiệu</h1>
        </div>
        <div class="d-flex justify-content-center">
            <v-form class="container row col-6">
                <v-text-field v-model="brand.name" label="Tên thương hiệu"></v-text-field>
                <v-textarea v-model="brand.description"></v-textarea>
                <div class="row">
                    <v-btn class="col" color="secondary" @click="back">Quay lại</v-btn>
                    <v-btn class="col" color="primary" @click="submit">Lưu</v-btn>
                </div>
            </v-form>
        </div>

    </div>
</template>
