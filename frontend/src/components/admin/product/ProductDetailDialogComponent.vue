<script lang="ts">
import {defineComponent} from "vue";
import {Product, ProductDetail} from "@/base/model/product.model";

export default defineComponent({
    name: 'ProductDetailDialogComponent',
    props: {
        product: Product,
    },
    data() {
        return {
            dialog: false,
            productDetails : [] as ProductDetail[],
            totalDetail: 0,
        }
    },
    /*
    export class ProductDetail {
    id?: number;
    size?: Size;
    color?: Color;
    product?: Product;
    quantity?: number;
    image?: string;
    sizeId?: number;
    colorId?: number;
    imageBase64?: string;
}
     */
})
</script>

<template>
    <v-dialog v-model="dialog" max-width="800px">
        <v-card>
            <v-card-title>
                <span class="headline">{{product.name}}</span>
            </v-card-title>
            <v-card-text>
                <!--cho chọn ảnh, thêm màu, thêm size-->
                <v-container v-for="(i,index) in totalDetail" :key="index">
                    <v-row>
                        <v-col cols="12" sm="6" md="4">
                            <v-card>
                                <v-img :src="productDetails[i].image" height="200px"></v-img>
                            </v-card>
                        </v-col>
                        <v-col cols="12" sm="6" md="4">
                            <v-card>
                                <v-card-title>
                                    <span class="headline">Size</span>
                                </v-card-title>
                                <v-card-text>
                                    <v-select
                                        v-model="productDetails[i].sizeId"
                                        :items="productDetails[i].product.sizeList"
                                        item-text="name"
                                        item-value="id"
                                        label="Size"
                                    ></v-select>
                                </v-card-text>
                            </v-card>
                        </v-col>
                        <v-col cols="12" sm="6" md="4">
                            <v-card>
                                <v-card-title>
                                    <span class="headline">Color</span>
                                </v-card-title>
                                <v-card-text>
                                    <v-select
                                        v-model="productDetails[i].colorId"
                                        :items="productDetails[i].product.colorList"
                                        item-text="name"
                                        item-value="id"
                                        label="Color"
                                    ></v-select>
                                </v-card-text>
                            </v-card>
                        </v-col>
                    </v-row>
                </v-container>
            </v-card-text>
            <v-card-actions>
                <v-btn color="blue darken-1" text @click="dialog = false">Đóng</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>