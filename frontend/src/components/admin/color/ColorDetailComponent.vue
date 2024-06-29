<script lang="ts">
import {defineComponent, inject} from "vue";
import {Color} from "@/base/model/product.model";
import {ColorService} from "@/base/service/color.service";
import {toast} from "vue3-toastify";
import {useRoute, useRouter} from "vue-router";

export default defineComponent({
    name: 'ColorDetailComponent',
    setup() {
        return {
            colorService: inject('colorService') as ColorService,
            router: useRouter(),
            route: useRoute()
        }
    },
    data() {
        return {
            color: new Color(),
            id: '' as string,
            colorList: [] as Color[],
            showColorPicker: false
        }
    },
    methods: {
        back() {
            this.router.push('/admin/color');
        },
        loadColorList() {
            this.colorService.findAll().then((data: Color[]) => {
                this.colorList = data;
            });
        },
        submit() {
            if (this.color.id) {
                this.colorService.update(this.color).then(() => {
                    this.router.push('/admin/color');
                    toast.success('Cập nhật thành công');
                });
            } else {
                this.colorService.save(this.color).then(() => {
                    this.router.push('/admin/color');
                    toast.success('Thêm mới thành công');
                });
            }
        },
    },
    created() {
        this.id = this.route?.params.id as string;
        this.colorService.get(this.id).then((data: Color) => {
            this.color = data;
        });
        this.loadColorList();
    }
})
</script>
<template>
    <div class="text-dark text-center">
        <div>
            <h1>Chi tiết màu</h1>
        </div>
        <div class="d-flex justify-content-center">
            <v-form class="container row col-6">
                <v-text-field v-model="color.name" label="Tên màu"></v-text-field>
                <div class="d-flex justify-content-between">
                    <v-label>Chọn màu</v-label>
                </div>
                <v-text-field
                    @click="showColorPicker = true" v-model="color.code" readonly
                    prepend-inner-icon="mdi-palette"></v-text-field>
                <v-dialog v-model="showColorPicker" width="290px">
                    <v-card
                        class="mx-auto"
                        max-width="344"
                        tile shaped
                    >
                        <v-card-title>
                            <span class="headline">Chọn 1 màu</span>
                        </v-card-title>
                        <v-card-text>
                            <v-color-picker
                                v-model="color.code"
                            ></v-color-picker>
                        </v-card-text>
                        <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn
                                color="primary"
                                text
                                @click="showColorPicker = false"
                            >
                                Đóng
                            </v-btn>
                            <v-btn
                                color="primary"
                                text
                                @click="showColorPicker = false"
                            >
                                OK
                            </v-btn>
                        </v-card-actions>
                    </v-card>
                </v-dialog>
                <div class="row">
                    <v-btn class="col" color="secondary" @click="back">Quay lại</v-btn>
                    <v-btn class="col" color="primary" @click="submit">Lưu</v-btn>
                </div>
            </v-form>
        </div>

    </div>
</template>
