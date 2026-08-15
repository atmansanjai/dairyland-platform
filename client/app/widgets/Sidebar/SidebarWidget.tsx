'use client'

import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarGroup,
  SidebarGroupContent,
  SidebarGroupLabel,
  SidebarHeader,
  SidebarMenu,
  SidebarMenuAction,
  SidebarMenuButton,
  SidebarMenuItem,
  SidebarProvider
} from '@/components/ui/sidebar'
import Link from 'next/link'
import React from 'react'
import { DynamicIcon } from 'lucide-react/dynamic'
import { contentMenus, footerMenus, sidebarMenus } from './Utils/SidebarMenus'

function GroupSidebarMenuItem({ menu }: { menu: sidebarMenus }) {
  const icon = menu.icon && 'menu'
  return (
    <SidebarMenuItem>
      <Link href={menu.href}>
        <SidebarMenuButton
          variant={'outline'}
          className={'relative'}>
          {menu.label}
          <SidebarMenuAction>
            <DynamicIcon name={icon} />
          </SidebarMenuAction>
        </SidebarMenuButton>
      </Link>
    </SidebarMenuItem>
  )
}

function GroupSidebar({ groupHeader, groupMenus }: { groupHeader: string; groupMenus: sidebarMenus[] }) {
  return (
    <SidebarGroup>
      <SidebarGroupLabel>{groupHeader}</SidebarGroupLabel>
      <SidebarGroupContent>
        <SidebarMenu>
          {groupMenus.map((menu, index) => (
            <GroupSidebarMenuItem
              key={index}
              menu={menu}
            />
          ))}
        </SidebarMenu>
      </SidebarGroupContent>
    </SidebarGroup>
  )
}

export default function SidebarWidget() {
  return (
    <SidebarProvider>
      <Sidebar>
        <SidebarHeader>
          <p>Dairy management</p>
        </SidebarHeader>
        <SidebarContent>
          <GroupSidebar
            groupHeader={'Content'}
            groupMenus={contentMenus}
          />
        </SidebarContent>
        <SidebarFooter>
          <GroupSidebar
            groupHeader={'Settings'}
            groupMenus={footerMenus}
          />
        </SidebarFooter>
      </Sidebar>
    </SidebarProvider>
  )
}
