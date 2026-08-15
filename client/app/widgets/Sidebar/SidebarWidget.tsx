import { Sidebar, SidebarContent, SidebarGroup, SidebarGroupContent,
    SidebarGroupLabel, SidebarHeader, SidebarMenu, SidebarMenuButton,
    SidebarMenuItem, SidebarProvider } from '@/components/ui/sidebar'
import Link from "next/link";

interface sidebarMenus {
    label: string;
    href: string;
}



export default function SidebarWidget() {

    const menus: sidebarMenus[] = [
        {
            label: "Admin",
            href: "/dashboard/admin"
        },
        {
            label: "Customer",
            href: "/dashboard/customer"
        },
        {
            label: "Milk",
            href: "/dashboard/milk"
        },
        {
            label: "Order",
            href: "/dashboard/order"
        }
    ]

  return (
      <SidebarProvider>
        <Sidebar>
            <SidebarHeader>
                <p>Dairy management</p>
            </SidebarHeader>
            <SidebarContent>
                <SidebarGroup>
                    <SidebarGroupLabel>Dashboard</SidebarGroupLabel>
                    <SidebarGroupContent>
                        <SidebarMenu>
                            {
                                menus.map((menu, index) => (
                                    <SidebarMenuItem key={index}>
                                            <Link href={menu.href}>
                                                <SidebarMenuButton className={"relative"}>
                                                {menu.label}
                                        </SidebarMenuButton>
                                            </Link>
                                    </SidebarMenuItem>
                                ))
                            }
                        </SidebarMenu>
                    </SidebarGroupContent>
                </SidebarGroup>
            </SidebarContent>
      </Sidebar>
      </SidebarProvider>
  )
}