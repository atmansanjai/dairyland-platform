import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from '@/components/ui/card'
import { Button } from '@/components/ui/button'
import { Field, FieldGroup, FieldLabel } from '@/components/ui/field'
import { Input } from '@/components/ui/input'

export default function LoginPage() {
  return (
    <section className={'flex h-screen flex-col items-center justify-center gap-5'}>
      <Card className={'w-[400px]'}>
        <CardHeader>
          <CardTitle>Login to your account</CardTitle>
          <CardDescription>Enter your ContactNumber below to login to your account</CardDescription>
        </CardHeader>
        <CardContent>
          <FieldGroup>
            <Field>
              <FieldLabel>ContactNumber</FieldLabel>
              <Input
                type='text'
                placeholder='Enter your ContactNumber'
              />
            </Field>
            <Field>
              <FieldLabel>Password</FieldLabel>
              <Input
                type='password'
                placeholder='Enter your Password'
              />
            </Field>
          </FieldGroup>
        </CardContent>
        <CardFooter className={'flex flex-col gap-3'}>
          <Button className={'w-full'}>Login</Button>
          <p>Don't have an account? Sign up</p>
        </CardFooter>
      </Card>
    </section>
  )
}
