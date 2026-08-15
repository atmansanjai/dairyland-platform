import React from 'react'
import { Footer, Header, SidebarWidget } from '@/app/widgets/'

export default function DashboardLayout({ children }: { children: React.ReactNode }) {
  return (
    <section className={'flex h-svh w-svw overflow-hidden'}>
      <aside>
        <SidebarWidget />
      </aside>
      <div className={'flex flex-1 flex-col'}>
        <header>
          <Header />
        </header>
        <main className={'flex-1'}>{children}</main>
        <footer>
          <Footer />
        </footer>
      </div>
    </section>
  )
}
