<script lang="ts">
import {defineComponent, inject} from "vue";
import {Size} from "@/base/model/product.model";
import {SizeService} from "@/base/service/size.service";
import {toast} from "vue3-toastify";
import {useRoute, useRouter} from "vue-router";

export default defineComponent({
    name: 'SizeDetailComponent',
    setup() {
        return {
            sizeService: inject('sizeService') as SizeService,
            router : useRouter(),
            route : useRoute()
        }
    },
    data() {
        return {
            size: new Size(),
            id: '' as string,
            sizeList: [] as Size[]
        }
    },
    methods: {
        back() {
            this.router.push('/admin/size');
        },
        loadSizeList() {
            this.sizeService.findAll().then((data: Size[]) => {
                this.sizeList = data;
            });
        },
        submit() {
            if (this.size.id) {
                this.sizeService.update(this.size).then(() => {
                    this.router.push('/admin/size');
                    toast.success('Cập nhật thành công');
                });
            } else {
                this.sizeService.save(this.size).then(() => {
                    this.router.push('/admin/size');
                    toast.success('Thêm mới thành công');
                });
            }
        }
    },
    created() {
        this.id = this.route?.params.id as string;
        this.sizeService.get(this.id).then((data: Size) => {
            this.size = data;
        });
        this.loadSizeList();
    }
})
</script>
<template>
    <div class="text-dark text-center">
        <div>
            <h1>Chi tiết kích cỡ</h1>
        </div>
        <div class="d-flex justify-content-center">
            <v-form class="container row col-6">
                <v-text-field type="number" v-model="size.name" label="Kích cỡ"></v-text-field>
                <div class="row">
                    <v-btn class="col" color="secondary" @click="back">Quay lại</v-btn>
                    <v-btn class="col" color="primary" @click="submit">Lưu</v-btn>
                </div>
            </v-form>
        </div>

    </div>
</template>
