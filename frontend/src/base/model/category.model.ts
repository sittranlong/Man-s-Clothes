import type {Tour} from "@/base/model/tour.model";

export class Category {
    id?: string;
    name?: string;
    description?: string;
    parent?: Category;
    parentId?: string;
    level?: number;
    children?: Category[];
    tours?: Tour[];
}