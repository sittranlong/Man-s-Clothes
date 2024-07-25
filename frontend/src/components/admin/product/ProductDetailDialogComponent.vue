<script lang="ts">
import {defineComponent, inject} from "vue";
import {Color, Product, ProductDetail, Size} from "@/base/model/product.model";
import {SizeService} from "@/base/service/size.service";
import {ColorService} from "@/base/service/color.service";
import {ProductService} from "@/base/service/product.service";
import {toast} from "vue3-toastify";

export default defineComponent({
    name: 'ProductDetailDialogComponent',
    props: {
        product: {
            type: Object as () => Product,
            required: true
        },
    },
    setup() {
        return {
            sizeService: inject('sizeService') as SizeService,
            colorService: inject('colorService') as ColorService,
            productService: inject('productService') as ProductService,
        }
    },
    data() {
        return {
            showDialog: false,
            productDetails: [] as ProductDetail[],
            sizeList: [] as Size[],
            colorList: [] as Color[],
            files: [] as File[]
        }
    },
    methods: {
        closeDialog() {
            this.showDialog = false;
        },
        async loadSizeList() {
            this.sizeList = await this.sizeService.findAll();
        },
        async loadColorList() {
            this.colorList = await this.colorService.findAll();
        },
        addDetail() {
            this.productDetails.push(new ProductDetail());
        },
        initializeProductDetail() {
            if (this.product?.id) {
                this.productDetails = this.product?.productDetails || [];
            } else {
                this.productDetails = [new ProductDetail()];
            }
        },
        async uploadFile(index: number) {
            if (this.files.length > 0) {
                const reader = new FileReader();
                const readFilePromise = new Promise((resolve, reject) => {
                    reader.onload = () => {
                        resolve(reader.result);
                    };
                    reader.onerror = (error) => {
                        reject(error);
                    };
                });
                reader.readAsDataURL(this.files[0]);
                try {
                    const avatarData = await readFilePromise;
                    this.productDetails[index].imageBase64 = avatarData as string;
                } catch (error) {
                    toast.error('Error reading file');
                } finally {
                    this.files = [];
                }
            }
        }, removeDetail(index: number) {
            const detail = this.productDetails[index];
            if (detail.id) {
                this.productService.deleteDetail(detail.id).then(() => {
                    this.productDetails.splice(index, 1);
                    toast.success('Xóa thành công');
                });
            } else {
                this.productDetails.splice(index, 1);
            }
        }
    },
    created() {
        this.loadSizeList();
        this.loadColorList();
        this.initializeProductDetail();
        console.log(this.productDetails, this.product.productDetails)
    }
})
</script>

<template>
    <v-btn class="d-flex" color="primary" @click="showDialog = true">Thêm chi tiết</v-btn>
    <v-dialog v-model="showDialog" max-width="800px" :persistent="true">
        <v-card>
            <v-card-title>
                <span class="headline">{{ product.name }}</span>
            </v-card-title>
            <v-card-text>
                <!--cho chọn ảnh, thêm màu, thêm size-->
                <v-container v-for="(i,index) in productDetails" :key="index">
                    <v-row>
                        <v-col cols="12" sm="6" md="4">
                            <span class="headline">Size</span>
                            <v-select
                                v-model="productDetails[index].sizeId"
                                :items="sizeList"
                                item-title="name"
                                item-value="id"
                                label="Size"
                            ></v-select>
                        </v-col>
                        <v-col cols="12" sm="6" md="4">
                            <span class="headline">Màu</span>
                            <v-select
                                v-model="productDetails[index].colorId"
                                :items="colorList"
                                item-title="name"
                                item-value="id"
                                label="Color"></v-select>
                        </v-col>
                    </v-row>
                    <v-row>
                        <v-col cols="12" sm="6" md="4">
                            <v-text-field
                                v-model="productDetails[index].quantity"
                                label="Số lượng"
                                type="number"
                            ></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="4" v-show="!productDetails[index].image">
                            <v-file-input
                                v-model="files"
                                label="Ảnh"
                                @change="uploadFile(index)"
                            ></v-file-input>
                        </v-col>
                        <v-col cols="12" sm="6" md="4" v-show="productDetails[index].image">
                            <v-img
                                role="button"
                                @click="() => productDetails[index].image = ''"
                                :src="'data:image/png;base64,' + productDetails[index].image"
                                width="100"
                                height="100"
                            ></v-img>
                        </v-col>
                    </v-row>
                    <v-row>
                        <v-col cols="12" sm="6" md="4">
                            <v-btn color="red" @click="removeDetail(index)">Xóa</v-btn>
                        </v-col>
                    </v-row>
                </v-container>
                <v-btn color="primary" @click="addDetail">Thêm</v-btn>
            </v-card-text>
            <v-card-actions>
                <v-btn color="blue darken-1" text @click="showDialog = false">Đóng</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>