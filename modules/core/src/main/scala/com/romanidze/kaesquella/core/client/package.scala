package com.romanidze.kaesquella.core

import com.romanidze.kaesquella.core.models.ClientError

package object client {
  type Output[A] = Either[ClientError, A]
}
