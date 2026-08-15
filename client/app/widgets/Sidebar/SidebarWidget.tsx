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

function GroupSidebarMenuItem({ menu, enableIcon }: { menu: sidebarMenus; enableIcon: boolean }) {
  const icon = menu.icon ?? 'menu'
  const Icon = (
    <SidebarMenuAction>
      <DynamicIcon name={icon} />
    </SidebarMenuAction>
  )

  return (
    <SidebarMenuItem>
      <Link href={menu.href}>
        <SidebarMenuButton
          variant={'outline'}
          className={'relative'}>
          {menu.label}
        </SidebarMenuButton>
        {enableIcon && Icon}
      </Link>
    </SidebarMenuItem>
  )
}

function GroupSidebar({
  groupHeader,
  groupMenus,
  enableIcon = false
}: {
  groupHeader: string
  groupMenus: sidebarMenus[]
  enableIcon?: boolean
}) {
  return (
    <SidebarGroup>
      <SidebarGroupLabel>{groupHeader}</SidebarGroupLabel>
      <SidebarGroupContent>
        <SidebarMenu>
          {groupMenus.map((menu, index) => (
            <GroupSidebarMenuItem
              key={index}
              menu={menu}
              enableIcon={enableIcon}
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
            enableIcon={true}
          />
        </SidebarFooter>
      </Sidebar>
    </SidebarProvider>
  )
}
