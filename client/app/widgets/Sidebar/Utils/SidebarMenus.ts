export interface sidebarMenus {
  label: string
  href: string
  icon?: string
}

export const contentMenus: sidebarMenus[] = [
  {
    label: 'Admin',
    href: '/dashboard/admin'
  },
  {
    label: 'Customer',
    href: '/dashboard/customer'
  },
  {
    label: 'Milk',
    href: '/dashboard/milk'
  },
  {
    label: 'Order',
    href: '/dashboard/order'
  },
  {
    label: 'Shipment',
    href: '/dashboard/shipment'
  },
  {
    label: 'Subscription',
    href: '/dashboard/subscription'
  },
  {
    label: 'Invoice',
    href: '/dashboard/invoice'
  },
  {
    label: 'Vendor',
    href: '/dashboard/vendor'
  }
]

export const footerMenus: sidebarMenus[] = [
  {
    label: 'Profile',
    href: '/dashboard/profile',
    icon: 'user-shield'
  },
  {
    label: 'Logout',
    href: '/login',
    icon: 'log-out'
  }
]
