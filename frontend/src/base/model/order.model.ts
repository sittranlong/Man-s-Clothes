import {User} from "@/base/model/user.model";
import {Tour} from "@/base/model/tour.model";
import type {Customer} from "@/base/model/customer.model";

export class Order {
    public static STATUS_PENDING = 0;
    public static STATUS_CONFIRMED = 1;
    public static STATUS_CANCELLED = 2;
    public static STATUS_COMPLETED = 3;
    public static STATUS_REFUNDED = 4;
    public static STATUS_EXPIRED = 5;
    public static STATUS_FAILED = 6;
    id?: string;
    code?: string;
    user?: User;
    tour?: Tour;
    quantityForAdult?: number;
    quantityForChildren?: number;
    quantityForBaby?: number;
    totalAdult?: number;
    totalChildren?: number;
    totalBaby?: number;
    totalPayment?: number;
    totalDiscount?: number;
    customers?: Customer[];
    status?: number;
    createDate?: Date;
}


export const OrderStatus = [
    {value: Order.STATUS_PENDING, text: 'Đang chờ xác nhận'},
    {value: Order.STATUS_CONFIRMED, text: 'Đã xác nhận'},
    {value: Order.STATUS_CANCELLED, text: 'Đã hủy'},
    {value: Order.STATUS_COMPLETED, text: 'Hoàn thành'},
    {value: Order.STATUS_REFUNDED, text: 'Đã hoàn tiền'},
    {value: Order.STATUS_EXPIRED, text: 'Hết hạn'},
    {value: Order.STATUS_FAILED, text: 'Thất bại'},
];

export const OrderStatusText = (status: number | undefined) => {
    if (status) {
        const orderStatus = OrderStatus.find(item => item.value === status);
        return orderStatus ? orderStatus.text : '';
    }
    return '';
};