import React from 'react'
import { Footer, Header, SidebarWidget } from '@/app/widgets/'


export default function DashboardLayout({children}: {children: React.ReactNode}) {
  return (
    <section className={"w-svw h-svh  overflow-hidden flex"}>
      <aside><SidebarWidget /></aside>
      <div className={"flex-1 flex flex-col"}>
        <header ><Header /></header>
        <main className={"flex-1"}>{children}</main>
        <footer><Footer /></footer>
      </div>
    </section>
  )
}